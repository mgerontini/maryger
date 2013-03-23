%%  Debug Point 5.
%
%   Prepare and store training data.
dinfo5      = load('DebugInfo/debuginfo5.mat');
np          = dinfo5.np;
nn          = dinfo5.nn;
all_ftypes  = dinfo5.all_ftypes;
stream      = RandStream('mt19937ar','seed', dinfo5.jseed);
RandStream.setDefaultStream(stream);

% GetTrainingData stores the training data in .mat files to use later.
GetTrainingData(all_ftypes, np, nn);

%%  Load training data.
Fdata   = load('FaceData.mat');
NFdata  = load('NonFaceData.mat');
FTdata  = load('FeaturesToUse.mat');







