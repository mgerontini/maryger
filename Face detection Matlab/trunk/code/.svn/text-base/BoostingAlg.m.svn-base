function [ Cparams ] = BoostingAlg( Fdata, NFdata, FTdata, T )
%BoostingAlg implements bosting algorithm as described in Algorithm 1, page
% 17 of the manual and Viola and Jones face detection paper
%
%   Fdata, NFdata, FTdata are structures containing training data - see
%   manual for further description
%   T is the number of iterations of the Boosting algorithm
%
%   Cparams is a structure of returning data - see code lines 63-65
%   alphas is a Tx1 row vector
%   Thetas is a Tx3 matrix


% Compute feature responses for all images for every feature type
ni = size(Fdata.ii_ims,1);% number of positive examples
m = size(NFdata.ii_ims,1);% number of negative examples
yfs = Fdata.ii_ims * FTdata.fmat;% (ni x k) k: number of features
nfs = NFdata.ii_ims * FTdata.fmat;% (m x k) k: number of features
fs = [yfs;nfs];% (n x k) k: number of features
n = size(fs,1);% This line can be ommitted

% Initialize weights
a = 1/(2*ni);
yws = repmat(a, ni, 1);
b = 1/(2*m);
nws = repmat(b, m, 1);
ws = [yws; nws];

% Generate ys vector
ys          = double([ones(ni,1); zeros(m,1)]);

% Normalize initial weights
weights     = ws/sum(ws);
fmat        = [];
all_ftypes  = [];

% Preallocate variables
Thetas  = zeros(T, 3);
theta   = zeros(1, size(fs,2));
p       = zeros(1, size(fs,2));
err     = zeros(1, size(fs,2));
alphas  = zeros(T, 1);

for t = 1:T
    % Normalize weights
    weights = weights/sum(weights);

    % Choose weak classifier with lowest error
    for i = 1:size(fs,2)
        [theta(i), p(i), err(i)] = LearnWeakClassifier(weights,...
            fs(:,i), ys);
    end
    
    [error I]   = min(err);
    Thetas(t,:) = [I, theta(I), p(I)];
    fmat        = [fmat FTdata.fmat(:,I)];
    all_ftypes  = [all_ftypes; FTdata.all_ftypes(I,:)];
    
    % Update weights
    beta        = error/(1-error);
    weights     = weights .* (beta.^(1 - abs(double(p(I) * fs(:,I) ...
                    < p(I)*theta(I))-ys)));
    
    % Set aplha values
    alphas(t,1) = log(1/beta);
end

% Construct returning structure
Cparams = struct( 'alphas', alphas, 'Thetas', Thetas, 'fmat',...
    fmat, 'all_ftypes', all_ftypes);

end

