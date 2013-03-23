%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%Function which applies the strong classifier
%to a test image 19x19
%output: a weighted sum of the weak classifier outputs
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
function score = ApplyDetector(Cparams, ii_im)

    % It extracts each feature from the strong classifier
    thres   = Cparams.Thetas(:,2);
    fmat    = Cparams.fmat;
    p       = Cparams.Thetas(:,3);
    alphas  = Cparams.alphas;

    % Reshape test image
    ii_im   = reshape(ii_im,[size(ii_im,1)*size(ii_im,2), 1]);

    % Calculate features for test image
    fs      = sum((ii_im' * fmat),1);
    fs      = fs';

    % Get H value 
    H       = (p .* fs) < (p .* thres); 

    % Get score
    score   = sum(alphas .* H);
end