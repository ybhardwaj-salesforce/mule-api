/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata.resolving;

import java.util.List;
import java.util.Optional;

/**
 * Container for the Metadata fetch operations provided by {@link MetadataKeysResolver}, {@link MetadataContentResolver}
 * and {@link MetadataOutputResolver} results.
 * Allows to communicate errors without propagating exceptions to the Metadata fetching service
 *
 * @param <T> return type of the Metadata resolving operation.
 * @since 1.0
 */
public interface MetadataResult<T>
{

    /**
     * @param payload object returned by the metadata operation
     * @return a success {@link MetadataResult} instance
     */
    static <T> MetadataResult<T> success(T payload)
    {
        return new ImmutableMetadataResult<>(payload);

    }

    /**
     * Creates a failure result obtaining the information from the occurred exception
     * when there is no payload for the result
     *
     * @param e exception that produced the failing {@link MetadataResult}
     * @return a failure {@link MetadataResult} instance
     */
    static <T> MetadataResult<T> failure(Exception e)
    {
        return new ImmutableMetadataResult<>(e);
    }

    /**
     * Used for transforming the payload of a result with an enriched new payload, while
     * preserving the original {@link MetadataResult} information
     *
     * @param newPayload     object returned by the metadata operation
     * @param originalResult original {@link MetadataResult} containing the information to propagate
     * @return a failure {@link MetadataResult} instance
     * @throws IllegalArgumentException if the original {@link MetadataResult} was not a failure
     */
    static <T> MetadataResult<T> failure(T newPayload, MetadataResult<?> originalResult)
    {
        if (!originalResult.getFailure().isPresent())
        {
            throw new IllegalArgumentException("A failing result was expected but no Failures were found");
        }

        return new ImmutableMetadataResult<>(newPayload, originalResult.getFailure().get());
    }

    /**
     * Creates a failure result obtaining the information from the occurred exception
     *
     * @param payload object returned by the metadata operation
     * @param e       exception that produced the failing {@link MetadataResult}
     * @return a failure {@link MetadataResult} instance
     */
    static <T> MetadataResult<T> failure(T payload, Exception e)
    {
        return new ImmutableMetadataResult<>(payload, e);
    }

    /**
     * Creates a failure result obtaining the information from the occurred exception
     * with a customized failure message
     *
     * @param payload object returned by the metadata operation
     * @param message a custom message describing the context in which the failure occurred
     * @param e       exception that produced the failing {@link MetadataResult}
     * @return a failure {@link MetadataResult} instance
     */
    static <T> MetadataResult<T> failure(T payload, String message, Exception e)
    {
        return new ImmutableMetadataResult<>(payload, message, e);
    }

    /**
     * @param payload object returned by the metadata operation
     * @param message a custom message describing the context in which the failure occurred
     * @param failure the {@link FailureCode} of the error that occurred
     * @param reason  the original message of the error that produced this failure
     * @return a failure {@link MetadataResult} instance
     */
    static <T> MetadataResult<T> failure(T payload, String message, FailureCode failure, String reason)
    {
        return new ImmutableMetadataResult<>(payload, new MetadataFailure(message, reason, failure));
    }

    /**
     * @return the object returned by the invoked Metadata operation
     */
    T get();

    /**
     * @return true if the operation was completed without errors, false otherwise
     */
    boolean isSuccess();

    /**
     * If {@link this#isSuccess} is false, then a {@link MetadataFailure} instance is provided
     * in order to describe the error that occurred during the invocation.
     *
     * @return a {@link List} of {@link MetadataFailure}s describing the errors that occurred during the invocation
     * if at least one occurred.
     */
    Optional<MetadataFailure> getFailure();

}