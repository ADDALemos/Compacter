lesson("A",1,1,3,25,"AS1D3").
lesson("A1",1,1,3,25,"A1S1D3").lesson("A2",1,2,4,25,"A2S1D4").
room("R",20).
room("A",25).
slot(1..30).
day(1..5).
lessonSlot(NL,S,D,Si,ID):-lesson(NL,ST,D,DU,Si,ID),S>=ST,S<ST+DU,slot(S).
1{assign(NL,NR,S,D,Id,Si,Sir):room(NR,Sir)}1:-lessonSlot(NL,S,D,Si,Id).
:-assign(NL,NR,S,D,Id,_,_),assign(NL1,NR,S,D,Id1,_,_),NL1!=NL,Id1!=Id.
:-assign(NL,NR,_,_,Id,_,_),assign(NL,NR1,_,_,Id,_,_),NR!=NR1.
value(Id,Si-Sir):-assign(_,_,_,_,Id,Si,Sir),Si>Sir.
a(V):-V=#sum{S,N:value(N,S)}.
#minimize{V:a(V)}.
#show assign/7.
#show a/1.