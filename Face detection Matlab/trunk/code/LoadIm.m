%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%Function which returns the image in its 
%double form and the integral image
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
function [im, ii_im] = LoadIm(im_fname)

    im      = imread(im_fname);

    if size(im, 3) == 3
        im = rgb2gray(im);
    end    
    
    im      = double(im);    
    mu      = mean(im(:));

    % Add a small value to sigma to avoid be zero
    sigma   = std(im(:)) + 1e-10;
    im(:)   = (im(:) - mu) / sigma;

    % Compute the integral image
    ii_im   = cumsum(cumsum(im, 1), 2);

    %check if data is normalized
    mean(im(:))
    std(double(im(:)))

    % %check if ii_im containt correct values
    % y_x_cum = ii_im(2,2)
    % y_x_sums = sum(sum(im(1:2,1:2)))
    % 
    % if y_x_cum == y_x_sums
    % fprintf('Sequential sums and cummulative sums are identical');
end



