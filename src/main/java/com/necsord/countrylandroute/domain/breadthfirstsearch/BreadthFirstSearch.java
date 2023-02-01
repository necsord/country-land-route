package com.necsord.countrylandroute.domain.breadthfirstsearch;

import lombok.experimental.UtilityClass;
import lombok.val;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@UtilityClass
public class BreadthFirstSearch {

    public static <T> Optional<List<T>> search(T start, T end, Function<T, Collection<T>> neighboursSupplier) {
        // Checking if nodes are the same
        if (start.equals(end)) {
            return Optional.of(List.of(start));
        }

        val visitedNodes = new HashSet<T>();
        val queue = new ArrayDeque<Node<T>>();
        queue.add(new Node<>(start));

        while (!queue.isEmpty()) {
            val currentNode = queue.remove();
            val neighbours = neighboursSupplier.apply(currentNode.value());

            for (T neighbour : neighbours) {
                // Don't visit the same node twice
                if (visitedNodes.contains(neighbour)) {
                    continue;
                }

                // Check if neighbour is the node we're looking for
                if (neighbour.equals(end)) {
                    return Optional.of(combinePath(currentNode, end));
                }

                visitedNodes.add(neighbour);
                queue.add(new Node<>(neighbour, currentNode));
            }
        }

        return Optional.empty();
    }

    private <T> List<T> combinePath(final Node<T> node, final T end) {
        val result = new ArrayList<T>();
        result.add(end);

        var currentNode = node;
        do {
            result.add(currentNode.value());
            currentNode = currentNode.previousNode();
        } while (currentNode != null);

        Collections.reverse(result);
        return result;
    }

}
