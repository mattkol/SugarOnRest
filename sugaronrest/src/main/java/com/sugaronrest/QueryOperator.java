package com.sugaronrest;

public enum QueryOperator {
    /**
     * Represents the QueryOperator enum class.
     *
     * The "equal" operator.
     */
    Equal,

    /**
     * The "greater than" operator.
     */
    GreaterThan,

    /**
     * The "greater than or equal to" operator.
     */
    GreaterThanOrEqualTo,

    /**
     * The "less than" operator.
     */
    LessThan,

    /**
     * The "less than or equal to" operator.
     */
    LessThanOrEqualTo,

    /**
     * The "contains" operator.
     */
    Contains,

    /**
     * Gets the starts with operator.
     */
    StartsWith,

    /**
     * The "ends with" operator.
     */
    EndsWith,

    /**
     * The "between" operator.
     */
    Between,

    /**
     * The "where in" operator.
     */
    WhereIn
}

