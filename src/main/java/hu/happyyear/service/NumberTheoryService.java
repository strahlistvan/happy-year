package hu.happyyear.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class NumberTheoryService {

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
    
    public Integer getBiggestHappyFactor(Integer number) {
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
}
