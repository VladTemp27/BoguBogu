import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
public class Main extends SortingAlgorithms implements Runnable {
    FileHandling fileHandler = new FileHandling();
    Scanner kInput = new Scanner(System.in);
    ArrayList<String[]> data = new ArrayList<>();
    String[] toSearch;


    public static void main(String[] args) {
        Main program = new Main();
        program.run();
    }//End of main method

    @Override
    public void run() {

        data = processFile(); // data is dynamic
        int rows_data =  data.size();
        toSearch = new String[rows_data - 1];
        promptMessage();
        printArray(toSearch);

    }//End of run method

    private void promptMessage() { // method binding (dynamic)
        System.out.println("Below are the categories for each datum in a data set");
        String[] arr_header = data.get(0); // header
        data.remove(0); // remove header in data set
        for(int i = 0; i < arr_header.length; i++)
        {
            System.out.print(i + 1 + "." + arr_header[i] + "\t");

        }

        int category_number;
        int sortingType;
        do {
            System.out.println("\nPlease input the category number to be sort: ");
            category_number = kInput.nextInt();
            System.out.println("Enter the sorting algorithm to be apply in data: 1. Bubble Sort | 2. Selection Sort | 3. Insertion Sort");
            sortingType = kInput.nextInt();

            if (!(category_number > 0  && category_number <= arr_header.length) || !(sortingType > 0  && sortingType < 4))
            {
                System.out.println("Please try again.");
            }
        } while (!(category_number > 0  && category_number <= arr_header.length) || !(sortingType > 0  && sortingType < 4));

        populateSubArray(data, toSearch, category_number - 1);
        sortingProcess(sortingType, toSearch);


    }

    /**
     * Method for checking the file with exception handling
     * @return data
     */
    private ArrayList<String[]> processFile(){
        boolean valid;
        ArrayList<String[]> data = new ArrayList<>();
        do {
            System.out.print("Enter file name: ");
            String fileName = kInput.nextLine();
            try {
                fileHandler.fileRead(fileName, data);
                valid = true;
            }catch(FileNotFoundException fileException){
                System.out.println("File not FOUND\n\n\n");
                valid = false;
            }
        }while(!valid);
        return data;
    }//End of processFile method

    /**
     * Method for printing an array
     * @param array
     */
    private void printArray(String[] array){
        for (Object o : array) {
            System.out.println(o);
        }
    }//End of printArray method

    /**
     * Method for populating an array
     * @param arrayList
     * @param arrayToPopulate
     * @param columnIndex
     **/
    private void populateSubArray(ArrayList<String[]> arrayList, String[] arrayToPopulate, int columnIndex){
        for(String[] stringItem; arrayList){
            String data = stringItem[columnIndex];
            arrayToPopulate = data;
        }
    }
    }//End of populateArray method




