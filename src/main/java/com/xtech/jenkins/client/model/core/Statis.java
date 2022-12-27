/*
 * Copyright (c) 2013 Cosmin Stejerean, Karl Heinz Marbaise, and contributors.
 *
 * Distributed under the MIT license: http://opensource.org/licenses/MIT
 */

package com.xtech.jenkins.client.model.core;

import lombok.Data;

import java.util.List;

/**
 * @author Karl Heinz Marbaise
 * <p>
 *         TODO: Has someone a better name for the class?
 */
@Data
public class Statis {

    private List<Double> history;
    private Double latest;
}
