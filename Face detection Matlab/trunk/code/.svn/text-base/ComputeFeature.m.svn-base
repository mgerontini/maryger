function [ fs ] = ComputeFeature( ii_ims, ftype )
%COMPUTEFEATURE Compute Integral Image Features
%   Extract the feature defined by ftype from each integral image defined
%   in ii_ims.
%
%   ii_ims: array of N integral images, size [w, h, N].
%   ftype:  array of feature parameters: [type, x, y, w, h].
%   fs:     Feature value for each image, so size 1xN.

    N       = length(ii_ims);
    fs      = zeros(1, N);
    type    = ftype(1);
    x       = ftype(2);
    y       = ftype(3);
    w       = ftype(4);
    h       = ftype(5);

    for i = 1:N
        ii_im   = ii_ims(:,:,i);
        
        f       = 0;
        if type == 1
            f = FeatureTypeI(ii_im, x, y, w, h);
        elseif type == 2
            f = FeatureTypeII(ii_im, x, y, w, h);
        elseif type == 3
            f = FeatureTypeIIII(ii_im, x, y, w, h);
        elseif type == 4
            f = FeatureTypeIV(ii_im, x, y, w, h);
        end
        fs(i) = f;
    end
end