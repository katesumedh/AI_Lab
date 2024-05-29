% Define facts for positive and negative words 
positive(good). 
positive(happy). 
positive(joy). 
positive(satisfied). 
positive(love). 
positive(like). 
negative(bad). 
negative(sad). 
negative(angry). 
negative(dissatisfied). 
negative(hate). 
negative(dislike). 
% Rule to check if a word is positive 
is_positive(Word) :- positive(Word). 
% Rule to check if a word is negative 
is_negative(Word) :- negative(Word). 
% Rule to analyze a sentence 
analyze_sentence([], 0, 0). 
analyze_sentence([H|T], PositiveCount, NegativeCount) :- 
analyze_sentence(T, PosCountRest, NegCountRest), 
(is_positive(H) -> PositiveCount is PosCountRest + 1, NegativeCount is NegCountRest; 
is_negative(H) -> NegativeCount is NegCountRest + 1, PositiveCount is PosCountRest; 
PositiveCount is PosCountRest, NegativeCount is NegCountRest). 
% Rule to determine the sentiment of a sentence 
sentiment(Sentence, Sentiment) :- 
analyze_sentence(Sentence, PositiveCount, NegativeCount), 
(PositiveCount > NegativeCount -> Sentiment = positive; Sentiment = negative).

Command
?- sentiment([happy, satisfied], Sentiment).
