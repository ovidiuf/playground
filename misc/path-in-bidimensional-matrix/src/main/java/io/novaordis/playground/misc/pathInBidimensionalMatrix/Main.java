package io.novaordis.playground.misc.pathInBidimensionalMatrix;

public class Main {

    public static void main(String[] args) {

        int[][] ti = { {1, 0, 0, 0}, {1, 0, 0, 0}, {1, 0, 0, 0}, {1, 1, 1, 9} };

        pathInBidimensionalMatrix(4, 4, ti);
    }

    private static void pathInBidimensionalMatrix(int rows, int columns, int[][] topologyInformation) {

        displayInput(rows, columns, topologyInformation);

        System.out.println();

        Location[][] locations = load(rows, columns, topologyInformation);

        displayLocations(locations);

        System.out.println();

        int distance = traverse(locations, 0, 0, 0);

        System.out.println("distance: " + distance);

        System.out.println();
    }

    /**
     * @return distance so far on a valid path, or -1 if this path is invalid
     */
    private static int traverse(
            Location[][] locations, int row, int column, int distanceSoFarIncludingJumpingInThisLocation) {

        System.out.println("traverse " + row + ", " + column + ", distance so far " + distanceSoFarIncludingJumpingInThisLocation);

        //
        // recurrence bottom out conditions
        //

        // outside bounds or inaccessible or already visited

        if (row < 0 || row >= locations.length) {

            System.out.println("    row outside bounds, returning -1");
            return -1;
        }

        if (column < 0 || column >= locations[0].length) {

            System.out.println("    column outside bounds, returning -1");
            return -1;
        }

        if (locations[row][column].destination) {

            //
            // we reached destination, valid path
            //

            System.out.println("    reached destination, returning " + distanceSoFarIncludingJumpingInThisLocation);
            return distanceSoFarIncludingJumpingInThisLocation;
        }

        if (!locations[row][column].accessible) {

            System.out.println("    inaccessible location, returning -1");
            return -1;
        }

        if (locations[row][column].visited) {

            System.out.println("    location already visited, returning -1");
            return -1;
        }

        //
        // accessible and not visited, on the path to destination
        //

        locations[row][column].visited = true;
        System.out.println("    marked " + row + ", " + column + " as visited");

        int up =
                traverse(locations, row - 1, column, distanceSoFarIncludingJumpingInThisLocation + 1);

        int right =
                traverse(locations, row, column + 1, distanceSoFarIncludingJumpingInThisLocation + 1);

        int left =
                traverse(locations, row, column - 1, distanceSoFarIncludingJumpingInThisLocation + 1);

        int down =
            traverse(locations, row + 1 , column, distanceSoFarIncludingJumpingInThisLocation + 1);

        int min = Integer.MAX_VALUE;

        if (up != -1 && up < min) {

            min = up;
        }
        if (right != -1 && right < min) {

            min = right;
        }
        if (left != -1 && left < min) {

            min = left;
        }
        if (down != -1 && down < min) {

            min = down;
        }

        if (min == Integer.MAX_VALUE) {

            min = -1;
        }

        return min;
    }

    private static class Location {

        private boolean accessible;
        private boolean destination;
        private boolean visited;
    }

    private static Location[][] load(int rows, int columns, int[][] topologyInformation) {

        Location[][] locations = new Location[rows][columns];

        for(int i = 0; i < rows; i ++) {

            for(int j = 0; j < columns; j ++) {

                int ti = topologyInformation[i][j];

                Location location = new Location();

                if (ti == 1) {

                    location.accessible = true;
                }
                else if (ti == 9) {

                    location.destination = true;
                }
                else if (ti == 0) {

                    location.accessible = false;
                }
                else {

                    throw new IllegalStateException("unknown state: " + ti);
                }

                locations[i][j] = location;
            }
        }

        return locations;
    }

    private static void displayInput(int rows, int columns, int[][] topologyInformation) {

        for(int i = 0; i < rows; i ++) {

            for(int j = 0; j < columns; j ++) {

                System.out.print(topologyInformation[i][j] + " ");
            }

            System.out.println();
        }
    }

    private static void displayLocations(Location[][] locations) {

        for(int i = 0; i < locations.length; i ++) {

            for(int j = 0; j < locations[0].length; j ++) {

                Location l = locations[i][j];

                if (l.accessible) {

                    System.out.print('*');
                }
                else if (l.destination) {

                    System.out.print('D');
                }
                else {

                    System.out.print('.');
                }

                System.out.print(' ');
            }

            System.out.println();
        }
    }
}
