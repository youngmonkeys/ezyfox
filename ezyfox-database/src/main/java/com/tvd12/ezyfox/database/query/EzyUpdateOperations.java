package com.tvd12.ezyfox.database.query;

import java.util.List;

import com.tvd12.ezyfox.function.EzyApply;

/**
 * <p> A nicer interface to the update operations in monogodb. All these operations happen at the server and can cause the server and
 * client
 * version of the Entity to be different </p>
 *
 * @param <T> The Java type used in the updates
 */
public interface EzyUpdateOperations<T> {

    /**
     * adds the value to an array field if it doesn't already exist in the array
     *
     * @param field the field to update
     * @param value the value to add
     * @return this
     */
    EzyUpdateOperations<T> addToSet(String field, Object value);

    /**
     * adds the values to an array field if they doesn't already exist in the array
     *
     * @param field  the field to update
     * @param values the values to add
     * @return this
     */
    EzyUpdateOperations<T> addToSet(String field, List<?> values);

    /**
     * Decrements the numeric field by 1
     *
     * @param field the field to update
     * @return this
     */
    EzyUpdateOperations<T> dec(String field);

    /**
     * Decrements the numeric field by value (must be a positive Double,
     * Float, Long, or Integer).
     *
     * @param field the field to update
     * @param value the value to decrement by
     * @throws IllegalArgumentException of the value is not an instance of
     *         Double, Float,Long, or Integer
     * @return this
     */
    EzyUpdateOperations<T> dec(String field, Number value);

    /**
     * Turns off validation (for all calls made after)
     *
     * @return this
     */
    EzyUpdateOperations<T> disableValidation();

    /**
     * Turns on validation (for all calls made after); by default validation is on
     *
     * @return this
     */
    EzyUpdateOperations<T> enableValidation();

    /**
     * Increments the numeric field by 1
     *
     * @param field the field to update
     * @return this
     */
    EzyUpdateOperations<T> inc(String field);

    /**
     * increments the numeric field by value (negatives are allowed)
     *
     * @param field the field to update
     * @param value the value to increment by
     * @return this
     */
    EzyUpdateOperations<T> inc(String field, Number value);

    /**
     * Enables isolation (so this update happens in one shot, without yielding)
     *
     * @return this
     */
    EzyUpdateOperations<T> isolated();

    /**
     * @return true if this update is to be run in isolation
     *
     * @since 1.3
     */
    boolean isIsolated();

    /**
     * Sets the numeric field to value if it is greater than the current value.
     *
     * @param field the field to update
     * @param value the value to use
     * @return this
     */
    EzyUpdateOperations<T> max(String field, Number value);

    /**
     * sets the numeric field to value if it is less than the current value.
     *
     * @param field the field to update
     * @param value the value to use
     * @return this
     */
    EzyUpdateOperations<T> min(String field, Number value);

    /**
     * Adds new values to an array field.
     *
     * @param field the field to updated
     * @param value the value to add
     * @return this
     */
    EzyUpdateOperations<T> push(String field, Object value);

    /**
     * Adds new values to an array field at the given position
     *
     * @param field   the field to updated
     * @param value   the value to add
     * @param options the options to apply to the push
     * @return this
     */
    EzyUpdateOperations<T> push(String field, Object value, EzyApply<EzyPushOptions> options);

    /**
     * Adds new values to an array field.
     *
     * @param field  the field to updated
     * @param values the values to add
     * @return this
     */
    EzyUpdateOperations<T> push(String field, List<?> values);

    /**
     * Adds new values to an array field at the given position
     *
     * @param field   the field to updated
     * @param values  the values to add
     * @param options the options to apply to the push
     * @return this
     */
    EzyUpdateOperations<T> push(String field, List<?> values, EzyApply<EzyPushOptions> options);

    /**
     * removes the value from the array field
     *
     * @param field the field to update
     * @param value the value to use
     * @return this
     */
    EzyUpdateOperations<T> removeAll(String field, Object value);

    /**
     * removes the values from the array field
     *
     * @param field  the field to update
     * @param values the values to use
     * @return this
     */
    EzyUpdateOperations<T> removeAll(String field, List<?> values);

    /**
     * removes the first value from the array
     *
     * @param field the field to update
     * @return this
     */
    EzyUpdateOperations<T> removeFirst(String field);

    /**
     * removes the last value from the array
     *
     * @param field the field to update
     * @return this
     */
    EzyUpdateOperations<T> removeLast(String field);

    /**
     * sets the field value
     *
     * @param field the field to update
     * @param value the value to use
     * @return this
     */
    EzyUpdateOperations<T> set(String field, Object value);

    /**
     * sets the field on insert.
     *
     * @param field the field to update
     * @param value the value to use
     * @return this
     */
    EzyUpdateOperations<T> setOnInsert(String field, Object value);

    /**
     * removes the field
     *
     * @param field the field to update
     * @return this
     */
    EzyUpdateOperations<T> unset(String field);}
