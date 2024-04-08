package com.project.atmiraFCT.service;

import com.project.atmiraFCT.exception.RecordNotFoundException;
import com.project.atmiraFCT.model.domain.Colaborator;
import com.project.atmiraFCT.model.domain.ColaboratorProject;
import com.project.atmiraFCT.model.domain.Project;
import com.project.atmiraFCT.model.domain.Task;
import com.project.atmiraFCT.repository.ColaboratorRepository;
import com.project.atmiraFCT.repository.ProjectRepository;
import com.project.atmiraFCT.repository.TaskRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;


@Service
public class TaskService implements StorageService {
    @Value("${media.location}")
    private String mediaLocation;

    private Path rootLocation;

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ColaboratorRepository colaboratorRepository;
    @Autowired
    private ProjectRepository projectRepository;


    /**
     * Obtiene una lista de tareas por ID de proyecto.
     *
     * @param projectId El ID del proyecto.
     * @return La lista de tareas asociadas al proyecto.
     */
    public List<Task> getTasksByProjectId(String projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    /**
     * Guarda una nueva tarea.
     *
     * @param title El título de la tarea
     * @param description  La descripción de la tarea.
     * @param objective    El objetivo de la tarea.
     * @param isClosed     Indica si la tarea está cerrada.
     * @param colaboratorId El ID del colaborador asignado a la tarea.
     * @param projectId    El ID del proyecto al que pertenece la tarea.
     * @return La tarea guardada.
     * @throws RecordNotFoundException Si no se encuentra el colaborador o el proyecto.
     */
    public Task saveTask( String title,String description, String objective, Boolean isClosed,String colaboratorId, String projectId) {
        Optional<Colaborator> colaboratorOptional = colaboratorRepository.findById(colaboratorId);
        Optional<Project> projectOptional = projectRepository.findById(projectId);

        if (colaboratorOptional.isPresent() && projectOptional.isPresent()) {
            Task task = new Task();
            task.setTitle(title);
            task.setDescription(description);
            task.setObjective(objective);
            task.setClosed(isClosed);
            task.setColaborator(colaboratorOptional.get());
            task.setProject(projectOptional.get());

            String idCode = generateTaskIdCode(projectOptional.get());
            task.setIdCode(idCode);

            Task savedTask = taskRepository.save(task);
             return savedTask;
        } else {
            throw new RecordNotFoundException("Colaborator or project not found");
        }
    }

    /**
     * Guarda una nueva subtarea.
     *
     * @param title El título de la tarea
     * @param description      La descripción de la subtarea.
     * @param objective        El objetivo de la subtarea.
     * @param isClosed         Indica si la subtarea está cerrada.
     * @param colaboratorId    El ID del colaborador asignado a la subtarea.
     * @param projectId        El ID del proyecto al que pertenece la subtarea.
     * @param parentTaskIdCode El código de identificación de la tarea padre.
     * @return La subtarea guardada.
     * @throws RecordNotFoundException Si no se encuentra el colaborador, el proyecto o la tarea padre.
     */
    public Task saveSubTask(String title,String description, String objective, Boolean isClosed, String colaboratorId, String projectId, String parentTaskIdCode) {
        Optional<Colaborator> colaboratorOptional = colaboratorRepository.findById(colaboratorId);
        Optional<Project> projectOptional = projectRepository.findById(projectId);

        if (colaboratorOptional.isPresent() && projectOptional.isPresent()) {

            if (!isValidParentTaskIdCodeFormat(parentTaskIdCode)) {
                throw new IllegalArgumentException("Invalid parentTaskIdCode format: " + parentTaskIdCode);
            }

            int nextSubTaskNumber = getNextSubTaskNumber(parentTaskIdCode);

            Task subTask = new Task();
            subTask.setTitle(title);
            subTask.setDescription(description);
            subTask.setObjective(objective);
            subTask.setClosed(isClosed);
            subTask.setColaborator(colaboratorOptional.get());
            subTask.setProject(projectOptional.get());


            String subTaskIdCode = parentTaskIdCode + "_" + nextSubTaskNumber;
            subTask.setIdCode(subTaskIdCode);

            Task parentTask = taskRepository.findByIdCode(parentTaskIdCode).orElseThrow(() -> new RecordNotFoundException("Parent task not found"));


            subTask.setTask(parentTask);


            Task savedSubTask = taskRepository.save(subTask);

            List<Task> subTasks = parentTask.getSubtareas();

            subTasks.add(savedSubTask);

            parentTask.setSubtareas(subTasks);

            taskRepository.save(parentTask);

            return savedSubTask;
        } else {
            throw new RecordNotFoundException("Colaborator or project not found");
        }
    }

    /**
     * Genera un código de identificación para la tarea basado en el proyecto.
     *
     * @param project El proyecto al que pertenece la tarea.
     * @return El código de identificación generado.
     */
    private String generateTaskIdCode(Project project) {
        int numberOfTasks = project.getTasks().size() + 1;
        return project.getId_code() + "_" + numberOfTasks;
    }

    /**
     * Verifica si el formato del código de identificación de la tarea padre es válido.
     *
     * @param parentTaskIdCode El código de identificación de la tarea padre.
     * @return true si el formato es válido, false de lo contrario.
     */
    private boolean isValidParentTaskIdCodeFormat(String parentTaskIdCode) {
        String[] parts = parentTaskIdCode.split("_");
        return parts.length >= 2;
    }

    /**
     * Obtiene el número de la siguiente subtarea.
     *
     * @param parentTaskIdCode El código de identificación de la tarea padre.
     * @return El número de la siguiente subtarea.
     */
    public int getNextSubTaskNumber(String parentTaskIdCode) {

        Task parentTask = taskRepository.findByIdCode(parentTaskIdCode)
                .orElseThrow(() -> new RecordNotFoundException("Parent task not found"));

        List<Task> subTasks = parentTask.getSubtareas();

        int nextSubTaskNumber = subTasks.size() + 1;

        return nextSubTaskNumber;
    }

    /**
     * Obtiene todas las tareas.
     *
     * @return La lista de todas las tareas.
     */
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    /**
     * Obtiene una tarea por su ID.
     *
     * @param id El ID de la tarea.
     * @return La tarea encontrada.
     * @throws RecordNotFoundException Si no se encuentra la tarea.
     */
    public Task getTaskById(String id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            return task.get();
        } else {
            throw new RecordNotFoundException("No task found with id: " + id);
        }
    }

    /**
     * Obtiene una tarea por su ID de proyecto y su código de identificación.
     *
     * @param projectId  El ID del proyecto.
     * @param taskIdCode El código de identificación de la tarea.
     * @return La tarea encontrada.
     * @throws RecordNotFoundException Si no se encuentra la tarea.
     */
    public Task getTaskByProjectIdAndTaskIdCode(String projectId, String taskIdCode) {
        Optional<Task> taskOptional = taskRepository.findByProjectIdAndTaskIdCode(projectId, taskIdCode);
        return taskOptional.orElseThrow(() -> new RecordNotFoundException("Task not found"));
    }

    /**
     * Elimina una tarea por su ID.
     *
     * @param id El ID de la tarea a eliminar.
     * @throws RecordNotFoundException Si no se encuentra la tarea.
     */
    public void deleteTask(String id) {
        Optional<Task> result = taskRepository.findById(id);
        if (result.isPresent()) {
            taskRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No task found with id: " + id);
        }
    }

    /**
     * Obtiene las subtareas de una tarea por el ID del colaborador.
     *
     * @param colaboratorId El ID del colaborador.
     * @return La lista de subtareas asociadas al colaborador.
     * @throws RecordNotFoundException Si no se encuentra el colaborador.
     */
    public List<Task> getTasksSubtaskByColaborator(String colaboratorId) {
        Optional<Colaborator> colaboratorOptional = colaboratorRepository.findById(colaboratorId);
        if (colaboratorOptional.isPresent()) {
            return taskRepository.findByColaborator(colaboratorOptional.get());
        } else {
            throw new RecordNotFoundException("Colaborator not found with id: " + colaboratorId);
        }
    }

    /**
     * Obtiene las subtareas de una tarea por el prefijo.
     *
     * @param prefix El prefijo del código de identificación de la tarea padre.
     * @return La lista de subtareas que coinciden con el prefijo.
     */
    public List<Task> getSubTasksByPrefix(String prefix) {
        return taskRepository.findSubtasksByParentTaskId(prefix);
    }

    /**
     * Actualiza el atributo 'isClosed' de una tarea.
     *
     * @param taskId  El ID de la tarea a actualizar.
     * @param isClosed El nuevo valor del atributo 'isClosed'.
     * @return La tarea actualizada.
     * @throws RecordNotFoundException Si no se encuentra la tarea.
     */
    public Task updateTask(String taskId, boolean isClosed) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            if (task.getClosed() == isClosed) {
                throw new IllegalArgumentException("You cannot set the task status to the same value");
            }
            task.setClosed(isClosed);
            return taskRepository.save(task); // Guardar los cambios y devolver la tarea actualizada
        } else {
            throw new RecordNotFoundException("Task not found with id: " + taskId);
        }
    }

    /**
     * Obtiene todas las tareas por ID de proyecto.
     *
     * @param projectId El ID del proyecto.
     * @return La lista de tareas asociadas al proyecto.
     * @throws RecordNotFoundException Si no se encuentra el proyecto.
     */
    public List<Task> getTasksByProject(String projectId) {
        Optional<Project> projectOptional = projectRepository.findById(projectId);

        if (projectOptional.isEmpty()) {
            throw new RecordNotFoundException("Project not found with id: " + projectId);

        } else {
            return taskRepository.findByProjectId(projectId);
        }
    }

    /**
     * Obtiene todas las tareas por ID de colaborador y de proyecto.
     *
     * @param colaboratorId El ID del colaborador.
     * @param projectId     El ID del proyecto.
     * @return La lista de tareas asociadas al colaborador y al proyecto.
     * @throws RecordNotFoundException Si no se encuentra el colaborador o el proyecto.
     */
    public List<Task> getTasksByColaboratorAndProject(String colaboratorId, String projectId) {
        Optional<Colaborator> colaboratorOptional = colaboratorRepository.findById(colaboratorId);
        Optional<Project> projectOptional = projectRepository.findById(projectId);
        if (colaboratorOptional.isPresent() && projectOptional.isPresent()) {
            return taskRepository.findByColaboratorAndProject(colaboratorOptional.get(), projectOptional.get());
        } else {
            throw new RecordNotFoundException("Colaborator or project not found");
        }
    }

    /**
     * Obtiene todas las tareas por ID de colaborador.
     *
     * @param colaboratorId El ID del colaborador.
     * @return La lista de tareas asociadas al colaborador.
     */
    public List<Task> getTasksByColaborator(Colaborator colaboratorId) {
        return taskRepository.findAllTasksByColaborator(colaboratorId);
    }

    /**
     * Inicializa el servicio de almacenamiento.
     *
     * @throws IOException Si hay un error al crear el directorio raíz.
     */
    @Override
    @PostConstruct
    public void init() throws IOException {
        rootLocation = Paths.get(mediaLocation);
        Files.createDirectories(rootLocation);
    }

    /**
     * Almacena un archivo en el sistema.
     *
     * @param file El archivo a almacenar.
     * @return El nombre del archivo almacenado.
     * @throws RuntimeException Si el archivo está vacío o si ocurre un error al almacenar el archivo.
     */
    @Override
    public String store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store entity file.");
            }
            String filename = file.getOriginalFilename();
            Path destinationFile = rootLocation.resolve(Paths.get(filename)).normalize().toAbsolutePath();
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
            return filename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    /**
     * Carga un archivo como recurso.
     *
     * @param filename El nombre del archivo a cargar.
     * @return El recurso cargado.
     * @throws RuntimeException Si no se puede leer el archivo.
     */
    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Could not read file: " + filename, e);
        }
    }

    public Task assignTaskToColaborator(String taskId, String colaboratorId) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        Optional<Colaborator> colaboratorOptional = colaboratorRepository.findById(colaboratorId);
        if(taskOptional.isPresent() && colaboratorOptional.isPresent()) {
            List<Colaborator> colaborators = new ArrayList<>();

            for (ColaboratorProject aux:taskOptional.get().getProject().getColaboratorProjects()){
                colaborators.add(aux.getColaborator());
            }

            if(!colaborators.isEmpty() && colaborators.contains(colaboratorOptional.get())){
                Task task = taskOptional.get();
                task.setColaborator(colaboratorOptional.get());
                return taskRepository.save(task);
            }else{
                throw new RecordNotFoundException("Colaborator not invited to the project");
            }
        } else {
            throw new RecordNotFoundException("Task or colaborator not found");
        }
    }


    /**
     * Actualiza una tarea existente.
     *
     * @param id           El ID de la tarea a actualizar.
     * @param updateTask La tarea actualizada.
     * @return La tarea actualizada.
     * @throws Exception Si no se encuentra el proyecto.
     */
    public Task updateTask(String id, Task updateTask) throws Exception {
        Optional<Task> result = taskRepository.findById(id);
        if (result.isPresent()) {
            Task task = result.get();
            task.setTitle(updateTask.getTitle());
            task.setDescription(updateTask.getDescription());
            task.setObjective(updateTask.getObjective());
            task.setClosed(updateTask.getClosed());
            return taskRepository.save(task);
        } else {
            throw new RecordNotFoundException("Task not found with id code: " );
        }
    }


}
