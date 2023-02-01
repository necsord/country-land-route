package com.necsord.countrylandroute.domain.breadthfirstsearch;

record Node<T>(T value, Node<T> previousNode) {
    public Node(final T value) {
        this(value,null);
    }
}
