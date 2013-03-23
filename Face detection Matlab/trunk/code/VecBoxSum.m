function [ b_vec ] = VecBoxSum( x, y, w, h, W, H )
%VECBOXSUM Compute the column vector b_vec.
%   If W and H are the dimensions of the integral image then this 
%   function returns the column vector b_vec which will be zeros, 
%   except for 4 elements such that: 
%
%   ii_im(:)' * b_vec equals ComputeBoxSum(ii_im, x, y, w, h)

    % Initialize parameters:
    a_vec = zeros(W, H);
    
    % Start with the big box from top left till x+w, y+w:
    a_vec(y + h - 1, x + w - 1) = 1;
    
    % Subtract the horizontal box above it:
    a_vec(y + h - 1, max(1, x - 1)) = -1;
    
    % Subtract the vertical box to the left:
    a_vec(max(1, y - 1), x + w - 1) = -1;
    
    % Re-add the box to the top-left:
    a_vec(max(1, y - 1), max(1, x - 1)) = 1;
    
    b_vec = a_vec(:);
end

