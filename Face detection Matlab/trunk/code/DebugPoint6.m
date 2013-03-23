%%   Debug point 6.
%
%   Verify boosting algorithm parameters.

% Load debug parameters.
clear;
dinfo6  = load('DebugInfo/debuginfo6.mat');

% Load test data.
Fdata   = load('FaceData.mat');
NFdata  = load('NonFaceData.mat');
FTdata  = load('FeaturesToUse.mat');
T       = dinfo6.T;
eps     = 0.2;

% Only use the first 1000 features:
FTdata.fmat = FTdata.fmat(:,1:1000);

% Run the boosting algorithm:
Cparams = BoostingAlg(Fdata, NFdata, FTdata, T);

% Assert identical results.
assert(sum(abs(dinfo6.alphas    - Cparams.alphas)    > eps) == 0, 'Alphas are not equal!');
assert(sum(abs(dinfo6.Thetas(:) - Cparams.Thetas(:)) > eps) == 0, 'Thetas are not equal!');

% Pass debug point 6:
fprintf('\nDebug point 6 passed (eps = %s).\n', eps);