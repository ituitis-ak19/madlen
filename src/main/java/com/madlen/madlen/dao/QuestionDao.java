package com.madlen.madlen.dao;

import com.madlen.madlen.dto.FilterDto;
import com.madlen.madlen.enums.CognitiveLevel;
import com.madlen.madlen.enums.DifficultyLevel;
import com.madlen.madlen.model.Question;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuestionDao {

    private final EntityManager entityManager;

    public QuestionDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Page<Question> getPageByCriteria(int page, int size, List<FilterDto> filters) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Question> criteriaQuery = criteriaBuilder.createQuery(Question.class);
        Root<Question> root = criteriaQuery.from(Question.class);
        Predicate predicate = criteriaBuilder.conjunction();
        predicate = this.getQuery(filters, criteriaBuilder, root, predicate, criteriaQuery);
        criteriaQuery.where(predicate);

        TypedQuery<Question> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(page * size);
        query.setMaxResults(size);

        List<Question> questions = query.getResultList();

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Question> countRoot = countQuery.from(Question.class);
        countQuery.select(criteriaBuilder.count(countRoot));
        Long totalCount = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(questions, PageRequest.of(page, size), totalCount);
    }

    private Predicate getQuery(List<FilterDto> filters, CriteriaBuilder criteriaBuilder, Root root, Predicate predicate, CriteriaQuery criteriaQuery) {
        if (filters != null) {
            for (FilterDto filter : filters) {
                Predicate fieldPredicate = null;
                for (int i = 0; i < filter.getValues().size(); i++) {
                    String logic = filter.getLogic();
                    String operator = filter.getOperators().size() > i ? filter.getOperators().get(i) : "";
                    String value = filter.getValues().get(i);

                    if (filter.getField().equals("course_name")) {
                        fieldPredicate = applyCourseName(value, fieldPredicate, criteriaBuilder, root.get(filter.getField()), logic);
                    } else if (filter.getField().equals("difficultyLevel")) {
                        fieldPredicate = ApplyDifficultyLevelFilter(value, fieldPredicate, criteriaBuilder, root.get(filter.getField()), logic);
                    } else if (filter.getField().equals("cognitiveLevel")) {
                        fieldPredicate = ApplyCognitiveLevelFilter(value, fieldPredicate, criteriaBuilder, root.get(filter.getField()), logic);
                    } else if (filter.getField().equals("contextPages")) {
                        fieldPredicate = applyContextPageFilter(value, fieldPredicate, criteriaBuilder, root.get(filter.getField()), logic);
                    } else if (filter.getField().equals("questionText")) {
                        fieldPredicate = applyQuestionTextFilter(value, fieldPredicate, criteriaBuilder, root.get(filter.getField()), logic);
                    }
                    predicate = (fieldPredicate == null) ? predicate : criteriaBuilder.and(predicate, fieldPredicate);
                }
            }
        }
        return predicate;
    }

    private Predicate applyCourseName(String value, Predicate fieldPredicate, CriteriaBuilder criteriaBuilder, Path<String> fieldPAth, String logic) {
        Predicate valuePredicate = criteriaBuilder.like(criteriaBuilder.lower(fieldPAth), value.toLowerCase() + "%");
        return combineFieldPredicate(fieldPredicate, valuePredicate, criteriaBuilder, logic);
    }

    private Predicate applyQuestionTextFilter(String value, Predicate fieldPredicate, CriteriaBuilder criteriaBuilder, Path<String> fieldPAth, String logic) {
        Predicate valuePredicate = criteriaBuilder.like(criteriaBuilder.lower(fieldPAth), "%" + value.toLowerCase() + "%");
        return combineFieldPredicate(fieldPredicate, valuePredicate, criteriaBuilder, logic);
    }


    private Predicate ApplyDifficultyLevelFilter(String value, Predicate fieldPredicate, CriteriaBuilder criteriaBuilder, Path<DifficultyLevel> fieldPath, String logic) {
        Predicate valuePredicate = criteriaBuilder.equal(fieldPath, DifficultyLevel.valueOf(value));
        return combineFieldPredicate(fieldPredicate, valuePredicate, criteriaBuilder, logic);
    }

    private Predicate ApplyCognitiveLevelFilter(String value, Predicate fieldPredicate, CriteriaBuilder criteriaBuilder, Path<CognitiveLevel> fieldPath, String logic) {
        Predicate valuePredicate = criteriaBuilder.equal(fieldPath, CognitiveLevel.valueOf(value));
        return combineFieldPredicate(fieldPredicate, valuePredicate, criteriaBuilder, logic);
    }

    private Predicate applyContextPageFilter(String value, Predicate fieldPredicate, CriteriaBuilder criteriaBuilder, Path<List<Integer>> fieldPath, String logic) {
        Predicate valuePredicate = null;
        try {
            Integer contextPageValue = Integer.parseInt(value);
            valuePredicate = criteriaBuilder.isMember(contextPageValue, fieldPath);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid context page value: " + value);
        }
        return combineFieldPredicate(fieldPredicate, valuePredicate, criteriaBuilder, logic);
    }

    private Predicate combineFieldPredicate(Predicate fieldPredicate, Predicate valuePredicate, CriteriaBuilder criteriaBuilder, String logic) {
        if (fieldPredicate == null) {
            return valuePredicate;
        }
        if (logic.equals("and")) {
            return criteriaBuilder.and(fieldPredicate, valuePredicate);
        } else {
            return criteriaBuilder.or(fieldPredicate, valuePredicate);
        }
    }
}
