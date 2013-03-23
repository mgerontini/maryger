function [ dets ] = ScanImageOverScale( Cparams, im_fname, min_s, max_s, step_s )
%SCANIMAGEOVERSCALE Scan image over variable scales.
%   

    [im, ii_im]     = LoadIm(im_fname); % Load image.
    dets            = zeros(0, 5);    
    
    for scale = min_s : step_s : max_s
        test_im             = imresize(im, scale);
        test_dets           = ScanImageFixedSize(Cparams, test_im);
        test_scale          = ones(size(test_dets));
        test_scale(:, 1:4)  = test_scale(:, 1:4) .* (1 / scale);
        test_dets           = test_scale .* test_dets;
        dets                = [dets; test_dets];
    end
    
end

