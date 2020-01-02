package hu.happyyear.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class NumberTheoryService {

    public static enum DivisorState { DEFICIENT, ABUNDANT, PERFECT };

    private int getSumOfDigitSquare(int num) {
        int sum = 0;
        while (num > 0) {
            sum+=(num%10)*(num%10);
            num/=10;
        }
        return sum;
    }

    public List<Integer> getChain(Integer number) {
        List<Integer> chain = new ArrayList<Integer>();
        chain.add(number);
        while (number!=89 && number!=1) {
            number = getSumOfDigitSquare(number);
            chain.add(number);
        }
        return chain;
    }

    public Boolean isHappyNumber(Integer number) {
        List<Integer> chain = getChain(number);
        return chain.contains(1);
    }

    public List<Integer> getPrimeFactors(Integer number) {
        Integer divider = 2;
        List<Integer> primeFactors = new ArrayList<Integer>();
        while (number > 1) {
            if (number%divider == 0) {
                number/=divider;
                primeFactors.add(divider);
            }
            else {
                ++divider;
            }
        }
        return primeFactors;
    }

    public List<Integer> getDividers(Integer number) {
        List<Integer> dividers = new ArrayList<Integer>();
        for (int divider = 1; divider < number; ++divider) {
            if (number%divider == 0) {
                dividers.add(divider);
            }
        }
        return dividers;
    }
    
    public Integer getBiggestHappyPrimeFactor(Integer number) {
        List<Integer> primeFactors = getPrimeFactors(number);
        Integer biggestHappyFactor = 1;
        for (Integer fact: primeFactors) {
            if (isHappyNumber(fact)) {
                biggestHappyFactor = fact;
            }
        }
        return biggestHappyFactor;
    }
    
    public Integer getBiggestHappyDivider(Integer number) {
        List<Integer> dividers = getDividers(number);
        Integer biggestHappyDivider = 1;
        for (Integer fact: dividers) {
            if (isHappyNumber(fact)) {
                biggestHappyDivider = fact;
            }
        }
        return biggestHappyDivider;
    }

    private void removeEveryNth(Integer stepSize, List<Integer> list) {
        Iterator<Integer> iterator = list.iterator();
        while( iterator.hasNext() ){
            for (int k=0; k<stepSize; ++k) {
                if (iterator.hasNext()) {
                    iterator.next();
                }
            }
            iterator.remove();
        }
    }

    public List<Integer> getLuckyNumbers(Integer limit) {
        List<Integer> luckyNumbers = new ArrayList<Integer>();
        for (int i=1; i<=limit; i+=2) {
            luckyNumbers.add(i);
        }
        for (int n=1; n<luckyNumbers.size()/2; ++n) {
            removeEveryNth(luckyNumbers.get(n), luckyNumbers);
        }
        return luckyNumbers;
    }

    public Boolean isLuckyNumber(Integer number) {
        List<Integer> luckyNumbers = getLuckyNumbers(2*number);
        return luckyNumbers.indexOf(number) != -1;
    }

    public Integer getNextLuckyNumber(Integer number) {
        List<Integer> luckyNumbers = getLuckyNumbers(2*number);
        if (isLuckyNumber(number)) {
            return luckyNumbers.get(luckyNumbers.indexOf(number) + 1);
        }

        int i;
        for (i=0; luckyNumbers.get(i) < number; ++i);
        return luckyNumbers.get(i);
    }

    public Integer getPrevLuckyNumber(Integer number) {
        List<Integer> luckyNumbers = getLuckyNumbers(2*number);
        if (isLuckyNumber(number)) {
            return luckyNumbers.get(luckyNumbers.indexOf(number) + 1);
        }

        int i;
        for (i=0; luckyNumbers.get(i) < number; ++i);
        return luckyNumbers.get(i-1);
    }

    public Integer getProperDivisorSum(Integer number) {
        List<Integer> dividers = getDividers(number);
        return dividers.stream().mapToInt(Integer::intValue).sum();
    }

    public DivisorState getDivisorState(Integer number) {
        Integer divSum = getProperDivisorSum(number);
        if (divSum < number) {
            return DivisorState.DEFICIENT;
        }
        if (divSum > number) {
            return DivisorState.ABUNDANT;
        }
        return DivisorState.PERFECT;
    }
}
