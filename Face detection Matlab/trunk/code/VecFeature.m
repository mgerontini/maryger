function [ ftype_vec ] = VecFeature( ftype, W, H )
%VECFEATURE Summary of this function goes here
%   Detailed explanation goes here

    % Extract feature parameters:
    x       = ftype(2);
    y       = ftype(3);
    w       = ftype(4);
    h       = ftype(5);

    switch ftype(1)
        case 1
            ftype_vec = VecBoxSum(x,     y,     w, h, W, H) - ...
                        VecBoxSum(x,     y + h, w, h, W, H);
        case 2
            ftype_vec = VecBoxSum(x + w, y,     w, h, W, H) - ...
                        VecBoxSum(x,     y,     w, h, W, H);
        case 3
            ftype_vec = VecBoxSum(x + w, y,     w, h, W, H) - ...
                        VecBoxSum(x,     y,     w, h, W, H) - ...
                        VecBoxSum(x+2*w, y,     w, h, W, H);
        case 4
            ftype_vec = VecBoxSum(x + w, y,     w, h, W, H) + ...
                        VecBoxSum(x,     y + h, w, h, W, H) - ...
                        VecBoxSum(x,     y,     w, h, W, H) - ...
                        VecBoxSum(x + w, y + h, w, h, W, H);
    end

end

