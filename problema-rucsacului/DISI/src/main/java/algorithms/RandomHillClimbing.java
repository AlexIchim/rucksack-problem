package algorithms;

import model.Bag;
import model.BagItem;
import utils.BestNeighbourType;
import utils.ClimbingType;
import utils.InfoReader;
import utils.SearchStrategy;

import javax.sound.midi.MidiDevice;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Created by Alex Ichim on 10.03.2017.
 */
public class RandomHillClimbing implements SearchStrategy {

    private String fileName;
    private InfoReader infoReader;
    private Bag bestBag;
    private List<List<String>> solutions;
    private int nrResets;
    private BestNeighbourType bestNeighbourType;
    private List<BagItem> bagItems;


    public RandomHillClimbing(String fileName, int resetCount, BestNeighbourType bestNeighbourType) {
        this.fileName = fileName;
        this.infoReader = InfoReader.readInfo(fileName);
        this.bestBag = new Bag();
        this.bagItems = infoReader.getBagItemList();
        this.solutions = new ArrayList<List<String>>();
        this.nrResets = resetCount;
        this.bestNeighbourType = bestNeighbourType;
    }

    public List<List<String>> getSolutions() {
        return solutions;
    }

    public void setSolutions(List<List<String>> solutions) {
        this.solutions = solutions;
    }

    public InfoReader getInfoReader() {
        return infoReader;
    }

    public void setInfoReader(InfoReader infoReader) {
        this.infoReader = infoReader;
    }

    public Bag getBestBag() {
        return bestBag;
    }

    public void setBestBag(Bag bestBag) {
        this.bestBag = bestBag;
    }

    public List<BagItem> getBagItems() {
        return bagItems;
    }

    public void setBagItems(List<BagItem> bagItems) {
        this.bagItems = bagItems;
    }




    public static void main(String[] args) {
       String fileName = "rucsac-200.txt";
    /*
        RandomHillClimbing randomHillClimbing = new RandomHillClimbing(fileName, 5000, BestNeighbourType.MaxValueRation);

        Bag bag = new Bag();
        bag = randomHillClimbing.randomHillClimbingAlg();
        List<List<String>> lists = randomHillClimbing.getSolutions();
        System.out.println(bag.getValue());*/

    }

    public Bag randomHillClimbingAlg() {
        int t = 0, bestValueSoFar = 0;
        boolean local;
        Bag bestBag = new Bag();

        do {
            local = false;
            /* Reset from random point */
            Bag newGeneratedBag = generateRandomBag(infoReader.getNrObjects(), bagItems, infoReader.getMaxWeight());
            if (t == 0)
                bestBag = newGeneratedBag;

            //Check if solutions already checked
            if (compareLists(solutions, newGeneratedBag.getBitsString())) {
                continue;
            }

            ArrayList<String> climbSteps = new ArrayList<>();
            climbSteps.add(newGeneratedBag.getBitsString());

            //Climb while not local optimum
            do {

                //Check all neighbors of a solution and find best neigbor improvement
                int bestIndex = getBestNeighbor(newGeneratedBag, bestNeighbourType);
                //found local optimum (no better neighbors)
                if (bestIndex == -1) {
                    local = true;
                }
                else {
                    newGeneratedBag.getItems().add(bagItems.get(bestIndex));
                    newGeneratedBag.setItemsBits(bagItems.size());
                    climbSteps.add(newGeneratedBag.getBitsString());
                    if (newGeneratedBag.getValue() > bestBag.getValue()) {
                        bestBag = newGeneratedBag;
                    }
                }
            } while (!local);

            if (bestBag.getValue() > bestValueSoFar) {
                bestValueSoFar = bestBag.getValue();
                solutions.clear();
                solutions.add(climbSteps);
            } else if (newGeneratedBag.getValue() == bestValueSoFar) { //Check for multiple solutions
                solutions.add(climbSteps);
            }
            t++;
        } while ( t < nrResets);
        return bestBag;
    }

    private int getBestNeighbor(Bag newBag, BestNeighbourType bestNeighbourType) {

        int newGeneratedBagValue    = newBag.getValue();
        int newGeneratedBagQuantity   = newBag.getQuantity();
        int bestIndex = -1;
        int bestValue = newGeneratedBagValue;
        int filterSoFar = 0;
        double filterRatio = 0;
        int filterQuantity = Integer.MAX_VALUE;
        for (int i = 0; i < bagItems.size(); i++) {
            if (newBag.getBitsString().charAt(i) == '0' &&
                newGeneratedBagQuantity + bagItems.get(i).getQuantity() <= infoReader.getMaxWeight())   {
                    switch (bestNeighbourType) {
                        case MaxValue:
                            if (bagItems.get(i).getValue() > filterSoFar) {
                                bestIndex = i;
                                filterSoFar = bagItems.get(i).getValue();
                            }
                            break;
                        case MaxValueRation:
                            if (bagItems.get(i).getRatio() > filterRatio) {
                                bestIndex = i;
                                filterRatio = bagItems.get(i).getRatio();
                            }
                            break;
                        case MinQuantity:
                            if (bagItems.get(i).getQuantity() < filterQuantity) {
                                bestIndex = i;
                                filterQuantity = bagItems.get(i).getQuantity();
                            }
                            break;
                    }

            }
        }
        return bestIndex;
    }


    /* Generate a random string of 1 and 0 of length nr of objects. */
    public static String generateRandomBitsString(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(2));
        }
        return sb.toString();
    }

    /* Check if generated string is valid */
    public static boolean checkValidSolution(List<BagItem> items, int maxWeight, String solution) {
        int totalWeight = 0;
        for (int i = 0; i < solution.length(); i++) {
            if (solution.charAt(items.get(i).getId() - 1) == '1' ) {
                totalWeight += items.get(i).getQuantity();
                if (totalWeight > maxWeight) {
                    return false;
                }
            }
        }
        return true;
    }

    /* Generate a valid random bag */
    public  Bag generateRandomBag(int nrItems, List<BagItem> bagItems, int maxWeight) {
        Bag bag = new Bag();
        boolean valid = false;
        while (!valid) {
            String solution = generateRandomBitsString(nrItems);
            if (checkValidSolution(bagItems, maxWeight, solution)) {
                for (int i = 0; i < solution.length(); i++) {
                    if (solution.charAt(i) == '1') {
                        bag.getItems().add(bagItems.get(i));
                    }
                }
                valid = true;
            }
        }
        bag.setItemsBits(infoReader.getNrObjects());
        return bag;
    }


    public Bag generateRandomBagUntilFull() {
        Bag bag = new Bag();
        bag.setMaxWeight(infoReader.getMaxWeight());
        List<Integer> itemsIndex = new ArrayList<>();
        for (int i = 0; i < infoReader.getNrObjects(); i++) {
            itemsIndex.add(i);
        }

        Random random = new Random();
        while (true) {
            //System.out.println(itemsIndex.size());
            int index = itemsIndex.get(random.nextInt(itemsIndex.size()));
            bag.getItems().add(infoReader.getBagItemList().get(index));
            //System.out.println("Adding item: " + index + " with value: " + infoReader.getBagItemList().get(index).getValue() + " for a total value: " + bag.getValue() + " and a total quantity of " + bag.getQuantity());
            if (bag.checkOverFull()) {
                bag.getItems().remove(infoReader.getBagItemList().get(index));
                //System.out.println("Removing item: " + index + " with value: " + infoReader.getBagItemList().get(index).getValue() + " for a total value: " + bag.getValue() + " and a total quantity of " + bag.getQuantity());
                break;
            }
            itemsIndex.remove((Integer) index);
        }
        bag.setItemsBits(infoReader.getNrObjects());
        return bag;
    }

    @Override
    public Bag findBestBag() {
        return  randomHillClimbingAlg();
    }


    private boolean compareLists(List<List<String>> list1, String solution) {
        boolean equals = false;
        int i =0;
        for (List<String> list: list1
             ) {
            if (list.get(0).equals(solution)) {
                equals = true;
            }
        }
        return equals;
    }


}
