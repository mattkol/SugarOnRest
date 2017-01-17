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

