%   Debug Point 4.
dinfo4          = load('DebugInfo/debuginfo4.mat');
dirname         = 'TrainingImages/FACES';
ni              = dinfo4.ni;
all_ftypes      = dinfo4.all_ftypes;
im_sfn          = 'FaceData.mat';
f_sfn           = 'FeaturesToMat.mat';
stream          = RandStream('mt19937ar','seed', dinfo4.jseed);
RandStream.setDefaultStream(stream);
ii_ims          = LoadSaveImData(dirname, ni, im_sfn);
fmat            = ComputeSaveFData(all_ftypes, f_sfn);

assert(sum(sum(dinfo4.fmat - fmat)) < 1e-6, 'fmat and dinfo4.fmat are not equal!');
assert(sum(sum(dinfo4.ii_ims - ii_ims)) < 1e-6, 'ii_ims and dinfo4.ii_ims are not equal!');

fprintf('\nDebug point 4 passed.\n');
