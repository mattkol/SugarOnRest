package com.sugaronrest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents Options class
 */
public class Options {
    /**
     * The default max result count
     */
    private static final int MaxCountResult = 100;

    /**
     * Initializes a new instance of the Options class
     */
    public Options()  {
        this.setMaxResult(MaxCountResult);
        this.setSelectFields(new ArrayList<String>());
    }

    /**
     * Gets or sets the current page number
     */
    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int value) {
        currentPage = value;
    }

    /**
     * Gets or sets the number of entities per page
     */
     public int getNumberPerPage() {
        return numberPerPage;
    }

    public void setNumberPerPage(int value) {
        numberPerPage = value;
    }

    /**
     * Gets or sets the max result entities to return
     */
    public int getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(int value) {
        maxResult = value;
    }

    /**
     * Gets or sets selected module fields to return
     */
    public List<String> getSelectFields() {
        return selectFields;
    }

    public void setSelectFields(List<String> value) {
        selectFields = value;
    }

    /**
     * Gets or sets the query.
     * If this value is set, the QueryPredicates value is ignored.
     */
    public String getQuery() {
        return query;
    }

    public void setQuery(String value) {
        query = value;
    }

    /**
     * Gets or sets the query parameters.
     */
    public List<QueryPredicate> getQueryPredicates() {
        return queryPredicates;
    }

    public void setQueryPredicates(List<QueryPredicate> value) {
        queryPredicates = value;
    }

    /**
     * Gets or sets the linked modules.
     * The "dictionary key" is the module name (e.g - Accounts, Leads etc) or .NET C# object type (e.g - typeof(Account), typeof(Lead)).
     * The "dictionary value" is the list of select fields.
     * The select fields (value) can be null or empty, but the module type or name (key) must be valid.
     */
     public Map<Object, List<String>> getLinkedModules() {
        return linkedModules;
    }

    public void setLinkedModules(Map<Object, List<String>>  value) {
        linkedModules = value;
    }

    private int currentPage;
    private int numberPerPage;
    private int maxResult;
    private List<String> selectFields;
    private String query;
    private List<QueryPredicate> queryPredicates;
    private Map<Object, List<String>> linkedModules;
}


