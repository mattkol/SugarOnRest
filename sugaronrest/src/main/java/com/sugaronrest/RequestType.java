package com.sugaronrest;

public enum RequestType
{
    /**
     * Represents RequestType enum class
     *
     * SugarCrm get by id method call
     */
    ReadById,

    /**
     * SugarCrm get all method call.
     */
    BulkRead,

    /**
     * SugarCrm get paged method call.
     */
    PagedRead,

    /**
     * SugarCrm create method call.
     */
    Create,

    /**
     * SugarCrm bulk create method call.
     */
    BulkCreate,

    /**
     * SugarCrm update method call.
     */
    Update,
    /**
     * SugarCrm bulk update method call.
     */
    BulkUpdate,

    /**
     * SugarCrm delete method call.
     */
    Delete,

    /**
     * SugarCrm get by id method call - this gets associated linked objects serialized into a known custom type.
     */
    LinkedReadById,

    /**
     * SugarCrm get all method call - this gets associated linked objects serialized into a known custom type.
     */
    LinkedBulkRead,


    /**
     * SugarCrm get all method call - this gets associated linked objects serialized into a known custom type.
     */
    AllModulesRead
}

