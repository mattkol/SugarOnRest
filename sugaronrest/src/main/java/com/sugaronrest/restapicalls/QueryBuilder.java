package com.sugaronrest.restapicalls;

import com.sugaronrest.QueryOperator;
import com.sugaronrest.QueryPredicate;
import org.apache.commons.lang.StringUtils;


import java.util.List;

/**
 * This class represents QueryBuilder class.
 * SugarCrm request query option builder.
 */
public final class QueryBuilder {
    /**
     * Build the where clause part of a SugarCrm query.
     *
     *  @param predicates The json predicates.
     *  @return The formatted query.
     */
    public static String getWhereClause(List<JsonPredicate> predicates) {
        if ((predicates == null) || (predicates.size() == 0)) {
            return StringUtils.EMPTY;
        }

        StringBuilder queryBuilder = new StringBuilder();
        String subQuery = StringUtils.EMPTY;
        for (JsonPredicate predicate : predicates)
        {
            switch(predicate.operator) {
                case Equal:
                    subQuery = predicate.isNumeric ? String.format("%s = %s", predicate.jsonName, predicate.value) : String.format("%s = '%s'", predicate.jsonName, predicate.value);
                    break;

                case GreaterThan:
                    subQuery = predicate.isNumeric ? String.format("%s > %s", predicate.jsonName, predicate.value) : String.format("%s > '%s'", predicate.jsonName, predicate.value);
                    break;

                case GreaterThanOrEqualTo:
                    subQuery = predicate.isNumeric ? String.format("%s >= %s", predicate.jsonName, predicate.value) : String.format("%s >= '%s'", predicate.jsonName, predicate.value);
                    break;

                case LessThan:
                    subQuery = predicate.isNumeric ? String.format("%s < %s", predicate.jsonName, predicate.value) : String.format("%s < '%s'", predicate.jsonName, predicate.value);
                    break;

                case LessThanOrEqualTo:
                    subQuery = predicate.isNumeric ? String.format("%s <= %s", predicate.jsonName, predicate.value) : String.format("%s <= '%s'", predicate.jsonName, predicate.value);
                    break;

                case Contains:
                    subQuery = predicate.jsonName + " LIKE '%" + predicate.value + "%'";
                    break;

                case StartsWith:
                    subQuery = predicate.jsonName + " LIKE '" + predicate.value + "%'";
                    break;

                case EndsWith:
                    subQuery = predicate.jsonName + " LIKE '%" + predicate.value + "'";
                    break;

                case Between:
                    subQuery = predicate.isNumeric ? String.format("%s BETWEEN %s AND %s", predicate.jsonName, predicate.fromValue, predicate.toValue) : String.format("%s BETWEEN '%s' AND '%s'", predicate.jsonName, predicate.fromValue, predicate.toValue);
                    break;

                case WhereIn:
                    subQuery = String.format("%s IN (%s)", predicate.jsonName, predicate.value);
                    break;
            }

            queryBuilder.append(subQuery);
            queryBuilder.append(" AND ");
        }

        String query = queryBuilder.toString();
        if (!StringUtils.isNotBlank(query))
        {
            return StringUtils.EMPTY;
        }

        query = StringUtils.removeEnd(query.trim(),"AND");
        query = " " + query.trim() + " ";
        return query;
    }

}


