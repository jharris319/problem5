Square Root Data Structure
--------------------------

A linked-list data structure, written in Java

#### Synopsis ####

The illusive "square root data-structure" maintains a Θ(sqrt(n)) time complexity for operations insert, delete, and find. This is achieved by structuring n items in a list into sqrt(n) rows, each containing sqrt(n) items. The data structure is sorted and maintains forward pointers. known as base elements, to every z elements. Should the number of elements between any two base elements exceed the square root bound, either the smallest item in the row is shifted to the preceeding row or the largest item is shifted to the following row. This shifting process operates in Θ(1) time and may cascade throughout the data structure causing at most Θ(sqrt(n)) total shifts.

#### Example ####

![Square Root DS] (https://github.com/jharris319/problem5/blob/master/sqrt.png)
