/*
 * Copyright (c) 2017 Nova Ordis LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.novaordis.playground;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 5/3/17
 */
public class FDAndLsofAnalysis {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    public static void run(String[] args) throws Exception {

        List<FileRepresentation> lsofFd = new ArrayList<>();
        List<FileRepresentation> fdFd = new ArrayList<>();

        parse(args, lsofFd, fdFd);

        System.out.println("lsof entries: " + lsofFd.size());
        System.out.println("fd entries:   " + fdFd.size());

        //
        // filter out all /proc/<pid>/fd entries - and report if the patch does not match
        //

        outer: for(Iterator<FileRepresentation> i = fdFd.iterator(); i.hasNext(); ) {

            FileRepresentation r = i.next();
            int fd = r.fd;

            for(Iterator<FileRepresentation> lsofI = lsofFd.iterator(); lsofI.hasNext(); ) {

                FileRepresentation lsofR = lsofI.next();

                if (lsofR.fd == fd) {

                    if(!r.path.equals(lsofR.path)) {

                        //
                        // this is a valid situation for sockets, pipes, etc
                        //

                        // System.err.println("FD " + fd + " (lsof line number " + lsofR.lineNumber + ", fd line number " + r.lineNumber + ") has different paths " + r.path + ", " + lsofR.path);
                    }

                    lsofI.remove();
                    i.remove();
                    continue outer;
                }
            }
        }

        //
        // fdFd should be empty
        //

        if (fdFd.size() != 0) {

            throw new IllegalStateException("fdFd should be empty");
        }

        System.out.println(lsofFd.size());

        for(FileRepresentation r: lsofFd) {

            System.out.println(r.lineNumber + ": " + r.fd + ", " + r.specialEntry + ", " + r.type + ", " + r.path);

        }

    }

    static void parse(String[] args, List<FileRepresentation> lsofFd, List<FileRepresentation> fdFd) throws Exception {

        String line;

        //
        // lsof output
        //

        BufferedReader br = new BufferedReader(new FileReader(new File(args[0])));

        long lineNumber = 0;

        while((line = br.readLine()) != null) {

            lineNumber ++;

            if(line.matches("^COMMAND.*")) {

                continue;
            }

            FileRepresentation fd = lsofLineParser(lineNumber, line);
            lsofFd.add(fd);
        }

        br.close();

        //
        // /proc/<pid>/fd directory listing in text format (ls -l *)
        //

        br = new BufferedReader(new FileReader(new File(args[1])));

        lineNumber = 0;

        while((line = br.readLine()) != null) {

            lineNumber ++;
            FileRepresentation fd = fdLineParser(lineNumber, line);
            fdFd.add(fd);
        }

        br.close();
    }

    //
    // COMMAND   PID    USER   FD   TYPE             DEVICE SIZE/OFF    NODE NAME
    // java    10016 vagrant  mem    REG              252,0   161704 1310723 /lib64/ld-2.12.so
    //
    static FileRepresentation lsofLineParser(long lineNumber, String line) throws Exception {

        String[] tokens = line.split("\\s+");

        Integer fd = null;

        String fds = tokens[3];

        if (fds.matches("^\\d.*")) {

            int end = fds.length() - 1;

            for(; end >= 0; end --) {

                if (Character.isDigit(fds.charAt(end))) {

                    break;
                }
            }

            try {

                fd = Integer.parseInt(fds.substring(0, end + 1));
            }
            catch(Exception e) {

                throw new IllegalStateException(e);
            }
        }

        String type = tokens[4];
        String path = tokens[8];

        if (fd != null) {

            return new FileRepresentation(lineNumber, fd.intValue(), path, type);
        }
        else {

            return new FileRepresentation(lineNumber, fds, path, type);
        }
    }

    //
    // lrwx------. 1 vagrant vagrant 64 May  3 09:45 1 -> /dev/pts/0 (deleted)
    //
    static FileRepresentation fdLineParser(long lineNumber, String line) throws Exception {

        String[] tokens = line.split("\\s+");

        return new FileRepresentation(lineNumber, Integer.parseInt(tokens[8]), tokens[10], null);
    }

// Attributes ------------------------------------------------------------------------------------------------------

// Constructors ----------------------------------------------------------------------------------------------------

// Public ----------------------------------------------------------------------------------------------------------

// Package protected -----------------------------------------------------------------------------------------------

// Protected -------------------------------------------------------------------------------------------------------

// Private ---------------------------------------------------------------------------------------------------------

// Inner classes ---------------------------------------------------------------------------------------------------

    private static class FileRepresentation {

        public FileRepresentation(long lineNumber, int fd, String path, String type) {

            this.lineNumber = lineNumber;
            this.fd = fd;
            this.path = path;
            this.type = type;
        }

        public FileRepresentation(long lineNumber, String specialEntry, String path, String type) {

            this.lineNumber = lineNumber;
            this.specialEntry = specialEntry;
            this.path = path;
            this.type = type;
        }

        public long lineNumber;
        public int fd = -1;
        public String path;
        public String type;
        public String specialEntry;
    }
}
