%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%Function which returns the image in its 
%double form and the integral image
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
function [im, ii_im] = LoadImFromImage(im)

    
    % Compute the integral image
    ii_im   = cumsum(cumsum(im, 1), 2);

    %check if data is normalized
    %mean(im(:))
    %std(double(im(:)))

    % %check if ii_im containt correct values
    % y_x_cum = ii_im(2,2)
    % y_x_sums = sum(sum(im(1:2,1:2)))
    % 
    % if y_x_cum == y_x_sums
    % fprintf('Sequential sums and cummulative sums are identical');
end



