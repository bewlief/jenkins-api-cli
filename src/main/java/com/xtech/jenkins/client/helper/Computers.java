package com.xtech.jenkins.client.helper;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.xtech.jenkins.client.model.computer.Computer;
import com.xtech.jenkins.client.model.computer.ComputerSet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 计算节点管理
 *
 * @author xtech
 */
public class Computers extends BaseManager {
    /**
     * Get a list of all the computers on the server (at the summary level)
     *
     * @return map of defined computers (summary level, for details @see
     * Computer#details
     * @throws IOException in case of an error.
     */
    public Map<String, Computer> getComputers() throws IOException {
        List<Computer> computers = getClient().get("computer/", Computer.class).getComputers();
        return Maps.uniqueIndex(computers, new Function<Computer, String>() {
            @Override
            public String apply(Computer computer) {
                computer.setClient(getClient());
                return computer.getDisplayName().toLowerCase();
            }
        });
    }

    /**
     * The ComputerSet class will give informations like
     * {@link ComputerSet#getBusyExecutors()} or the
     * {@link ComputerSet#getTotalExecutors()}.
     *
     * @return {@link ComputerSet}
     * @throws IOException in case of an error.
     */
    public ComputerSet getComputerSet() throws IOException {
        ComputerSet computerSet = getClient().get("computer/?depth=2", ComputerSet.class);
        computerSet.setClient(getClient());
        return computerSet;
    }

    @Override
    protected String[] getDependencyArray() {
        return null;
    }
}
