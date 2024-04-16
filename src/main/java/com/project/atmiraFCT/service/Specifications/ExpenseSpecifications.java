package com.project.atmiraFCT.service.Specifications;

import com.project.atmiraFCT.model.domain.Expense;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class ExpenseSpecifications {
    /**
     * Construye una consulta de JPA en base a los criterios para filtrar gastos según el id del proyecto,
     * el id del colaborador y la fecha del ticket. Este método permite crear consultas dinámicas
     * basadas en los criterios proporcionados. Si un parámetro es {@code null}, el filtro
     * correspondiente no se aplicará.
     *
     * @param idProject El identificador del proyecto. Si se proporciona, el filtro buscará gastos
     *                  asociados a este proyecto específico. El filtro realiza una búsqueda parcial,
     *                  coincidiendo cualquier parte del id del proyecto con el valor proporcionado.
     * @param idAlias   El identificador del colaborador. Si se proporciona, el filtro buscará gastos
     *                  asociados a este colaborador específico. Al igual que con el id del proyecto,
     *                  realiza una búsqueda parcial.
     * @param date      La fecha del ticket en formato "yyyy-MM-dd". Si se proporciona, el filtro buscará
     *                  gastos que coincidan exactamente con esta fecha. Si la fecha es inválida o no
     *                  sigue el formato esperado, se lanzará una excepción {@link RuntimeException}.
     *
     * @return Una especificación de Criteria que puede ser utilizada con el API de Criteria de JPA
     *         para construir una consulta de base de datos.
     * @throws RuntimeException si hay un error al parsear la fecha.
     */
    public static Specification<Expense> withFilter(String idProject, String idAlias, String date) {
        return (root, query, cb) -> {
            Predicate p = cb.conjunction();

            // Para el id del proyecto
            if (idProject != null) {
                p = cb.and(p, cb.like(root.get("project").get("id"), "%" + idProject + "%"));
            }

            // Para el id del colaborador
            if (idAlias != null) {
                p = cb.and(p, cb.like(root.get("colaborator").get("id"), "%" + idAlias + "%"));
            }

            // Para la fecha
            if (date != null) {
                LocalDate ticketDate;
                try {
                    ticketDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                } catch (DateTimeParseException e) {
                    throw new RuntimeException("Error parsing date: " + date, e);
                }
                p = cb.and(p, cb.equal(root.get("ticketDate"), ticketDate));
            }
            return p;
        };
    }
}
