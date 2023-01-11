package com.marketplacehn.utils;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SortingUtils {

    private List<Sort.Order> sortingParams;

    //sortinArgs = {"field, direction", "field, direction"}
    public List<Sort.Order> getSortingOrder(final String[] sortingArgs){

        if(sortingArgs.length > 1){
            for(final String sortOrder : sortingArgs){
                final String[] sortIndividualArgs = sortOrder.split(",");
                final String direction = sortIndividualArgs[0];
                addSortingParams(direction, sortIndividualArgs[1]);
            }
        }
        else {
            addSortingParams(sortingArgs[1].trim(), sortingArgs[0].trim());
        }
        return sortingParams;
    }

    /**
     * Return sortDirection given string.
     * @param sortDirection String
     * @return Direction
     */
    private void addSortingParams(final String sortDirection, final String fieldName){
        Sort.Direction dir;
        if(sortDirection.equals("desc")){
            dir = Sort.Direction.DESC;
        }
        else{
            dir = Sort.Direction.ASC;
        }
        sortingParams.add(new Sort.Order(dir, fieldName));
    }

}
