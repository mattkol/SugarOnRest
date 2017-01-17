/**
 MIT License

 Copyright (c) 2017 Kola Oyewumi

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */

package com.sugaronrest.restapicalls;

import org.apache.commons.lang.StringUtils;

import java.util.List;


public final class QueryBuilder {

    /**
     * Build the where clause part of a SugarCRM query.
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


