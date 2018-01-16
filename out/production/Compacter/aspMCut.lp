lesson("A",12,1,1,20,"AS12D1").
lesson("A1",13,1,1,20,"A1S14D1").
lesson("A2",12,1,1,20,"A2S12D2").
lesson("A4",13,1,1,20,"A4S12D2").
lesson("A3",13,1,1,50,"A3S13D1").
room("R",50).
room("A",50).
slot(1..22).
day(1..5).
lessonSlot(NL,S,D,Si,ID):-lesson(NL,ST,D,DU,Si,ID),S>=ST,S<ST+DU,slot(S).
{assign(NL,NR,S,D,Id,Si,Sir):room(NR,Sir)}1:-lessonSlot(NL,S,D,Si,Id).
:-assign(NL,NR,S,D,Id,_,_),assign(NL1,NR,S,D,Id1,_,_),NL1!=NL,Id1!=Id.
:-assign(NL,NR,_,_,Id,_,_),assign(NL,NR1,_,_,Id,_,_),NR!=NR1.
:-assign(NL,NR,_,_,Id,_,_),assign(NL1,NR1,_,_,Id1,_,_),NR!=NR1,room(NR,S),room(NR1,S),lesson(NL,S,D,DU,_,Id),lesson(NL1,S,D,DU,_,Id1),NL>NL1,Id>Id1.

value(Id,Si):-assign(_,_,_,_,Id,Si,Sir),Sir>=Si.
value(Id,Sir):-assign(_,_,_,_,Id,Si,Sir),Sir<Si.
a(V):-V=#sum{S,N:value(N,S)}.
#maximize{V:a(V)}.
#show assign/7.
#show a/1.
