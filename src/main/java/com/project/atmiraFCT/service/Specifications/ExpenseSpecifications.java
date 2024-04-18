package com.project.atmiraFCT.service.Specifications;

import com.project.atmiraFCT.model.Enum.TypeExpensive;
import com.project.atmiraFCT.model.domain.Expense;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class ExpenseSpecifications {
    public static Specification<Expense> withFilter(Expense expense) {
        return (root, query, cb) -> {
            Predicate p = cb.conjunction();

            // Agregamos condiciones específicas si no son nulas
            if (expense.getTicketId() != null) {
                p = addTicketIdPredicate(p, root, cb, expense.getTicketId());
            }
            if (expense.getTicketDate() != null) {
                p = addTicketDatePredicate(p, root, cb, expense.getTicketDate());
            }
            if (expense.getCreatedDate() != null) {
                p = addCreatedDatePredicate(p, root, cb, expense.getCreatedDate());
            }
            if (expense.getCost() != null) {
                p = addCostPredicate(p, root, cb, expense.getCost());
            }
            if (expense.getDescription() != null) {
                p = addDescriptionPredicate(p, root, cb, expense.getDescription());
            }
            if (expense.getState() != null) {
                p = addStatePredicate(p, root, cb, expense.getState());
            }
            if (expense.getProject() != null) {
                p = addProjectPredicate(p, root, cb, expense.getProject().getId_code());
            }
            if (expense.getColaborator() != null) {
                p = addColaboratorPredicate(p, root, cb, expense.getColaborator().getId_alias());
            }
            if (expense.getTypeExpensive() != null) {
                p = addTypeExpensivePredicate(p, root, cb, expense.getTypeExpensive());
            }

            return p;
        };
    }

    private static Predicate addTicketIdPredicate(Predicate p, Root<Expense> root, CriteriaBuilder cb, String ticketId) {
        return cb.and(p, cb.equal(root.get("ticketId"), ticketId));
    }

    private static Predicate addTicketDatePredicate(Predicate p, Root<Expense> root, CriteriaBuilder cb, LocalDate ticketDate) {
        return cb.and(p, cb.equal(root.get("ticketDate"), ticketDate));
    }

    private static Predicate addCreatedDatePredicate(Predicate p, Root<Expense> root, CriteriaBuilder cb, Date createdDate) {
        return cb.and(p, cb.equal(root.get("createdDate"), createdDate));
    }

    private static Predicate addCostPredicate(Predicate p, Root<Expense> root, CriteriaBuilder cb, Integer cost) {
        return cb.and(p, cb.equal(root.get("cost"), cost));
    }

    private static Predicate addDescriptionPredicate(Predicate p, Root<Expense> root, CriteriaBuilder cb, String description) {
        return cb.and(p, cb.like(root.get("description"), "%" + description + "%"));
    }

    private static Predicate addStatePredicate(Predicate p, Root<Expense> root, CriteriaBuilder cb, Boolean state) {
        return cb.and(p, cb.equal(root.get("state"), state));
    }

    private static Predicate addProjectPredicate(Predicate p, Root<Expense> root, CriteriaBuilder cb, String projectId) {
        return cb.and(p, cb.equal(root.get("project").get("id"), projectId));
    }

    private static Predicate addColaboratorPredicate(Predicate p, Root<Expense> root, CriteriaBuilder cb, String colaboratorId) {
        return cb.and(p, cb.equal(root.get("colaborator").get("id"), colaboratorId));
    }

    private static Predicate addTypeExpensivePredicate(Predicate p, Root<Expense> root, CriteriaBuilder cb, TypeExpensive typeExpensive) {
        return cb.and(p, cb.equal(root.get("typeExpensive"), typeExpensive));
    }
}
