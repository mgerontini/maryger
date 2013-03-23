function [ cpic ] = ...
    MakeClassifierPic( all_ftypes, chosen_f, alphas, ps, W, H )
%MakeClassifierPic creates a picture for each feature in chosen_f via 
% MakeFeaturePic and then takes the weighted sum of those pictures
%   
%   all_types: array defining each feature
%   chosen_f: a vector of postive integers that correspond to the features
%             used in the classifier
%   alphas: weights associated with each feature/weak classifier
%   W: width of image
%   H: height of image
%
%   cpic: weighted sum of the pictures corresponding to features in 
%         chosen_f
%
% For each feature in chosen_f create its picture and store it as a column
% in pics matrix
%%%%%%%%%%%%%%%%%%%%%%%vectorization???
cpic = zeros(H, W); % This MAY need to be W,H!

for i = 1:length(chosen_f)
    fpic    = MakeFeaturePic(all_ftypes(chosen_f(i), :), W, H);
    cpic    = cpic + (alphas(i) * ps(i)) * fpic;
end

end

