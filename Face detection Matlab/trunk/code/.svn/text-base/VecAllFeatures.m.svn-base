function [ fmat ] = VecAllFeatures( all_ftypes, W, H )
%VECALLFEATURES Summary of this function goes here
%   Detailed explanation goes here
    nf   = length(all_ftypes);
    fmat = zeros(W * H, nf);
    
    for i = 1:nf
        fmat(:,i) = VecFeature(all_ftypes(i,:), W, H);
    end
end

