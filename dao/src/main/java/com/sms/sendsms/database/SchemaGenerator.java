package com.sms.sendsms.database;

import com.sms.sendsms.database.schemas.*;

import de.greenrobot.daogenerator.DaoGenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
* Use this class to generate DB Schema
* */
public class SchemaGenerator {

    private final static String OUTPUT_DIRECTORY = "gen";

    public static void main(String[] args) throws Exception {
        List<SchemaVersion> versions = new ArrayList<SchemaVersion>();

        versions.add(new Version1(false));
        versions.add(new Version2(true));

        verifySchemas(versions);

        for (SchemaVersion version : versions) {
            new DaoGenerator().generateAll(version.getSchema(), OUTPUT_DIRECTORY);
        }
    }

    public static void verifySchemas(List<SchemaVersion> versions) throws IllegalArgumentException {

        int currentlySetSchemas = 0;

        Set<Integer> versionNumbers = new HashSet<Integer>();

        for (SchemaVersion version : versions) {

            if (version.isCurrent()) {
                currentlySetSchemas++;
            }

            int versionNumber = version.getVersionNumber();
            if (versionNumbers.contains(versionNumber)) {
                throw new IllegalArgumentException(
                    "Unable to process schema versions, multiple instances with version number : "
                        + version.getVersionNumber());
            }
            versionNumbers.add(versionNumber);
        }

        if (currentlySetSchemas != 1) {
            throw new IllegalArgumentException(
                "Unable to generate schema, exactly one schema marked as current is required.");
        }
    }
}
