function [ theta, p, err ] = LearnWeakClassifier( ws, fs, ys )
%LearnWeakClassifier implements Algorithm 2 page 19 of the Manual.
% It takes as input the vector of weights associated with each training
% image(ws), a vector containing the value of a particular feature 
% extracted from each training image(fs) and a vector of the labels
% associated with each training image(ys).
% The output are then the learnt parameters of the weak classifer and its
% associated error.

% Compute weighted mean of positive and negative examples
% For dot product we got some NaN values in the vectors and the total
% result returned NaN, so we tried this approach
ws      = ws';
meanPos = (ws * (fs .*ys))/(ws * ys);
meanNeg = (ws* (fs .*(1-ys)))/(ws * (1-ys));

% Set threshold theta
theta = .5 * (meanPos + meanNeg);

% Compute error associated with the two possible values of the parity
% for p = -1
pVal = [-1, 1];
h = (-1)*fs < (-1)*theta;
e(1) = ws * abs(ys - h);

% for p = 1
h = fs < theta;
e(2) = ws * abs(ys - h);

[err, I] = min(e);

p = pVal(I);

end

