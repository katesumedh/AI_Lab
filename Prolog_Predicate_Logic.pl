% Define facts and rules for a family tree with Indian names.


% Facts: Define parent-child relationships.
parent(ram, priya).
parent(ram, anil).
parent(priya, amita).
parent(anil, anjali).
parent(shyam, ram).
parent(shyam, priya).


% Rules: Define sibling and grandparent relationships.
sibling(X, Y) :-
    parent(Z, X),
    parent(Z, Y),
    X \= Y.


grandparent(X, Y) :-
    parent(X, Z),
    parent(Z, Y).


% Sample queries:


% Check if Ram is the parent of Priya.
% ?- parent(ram, priya).
% Output: true


% Find siblings of Anil.
% ?- sibling(anil, Sibling).
% Output: Sibling = priya


% Find the grandparents of Amita.
% ?- grandparent(Grandparent, amita).
% Output: Grandparent = ram
