
ii_ims = zeros(19,19,100);
mystr = ['TrainingImages/FACES', '/*.bmp'];
im_files = dir(mystr);
addpath('TrainingImages/FACES');

numPictures = length(im_files);
len         = numPictures;


for i = 1:100
    [im, ii_im] = LoadIm(im_files(i).name);
    c = ii_im;
    ii_ims(:,:,i) = ii_im;    
end

dinfo3  = load('DebugInfo/debuginfo3.mat');
ftype   = dinfo3.ftype;
eps     = 1e-6;
sum(abs(dinfo3.fs - ComputeFeature(ii_ims, ftype)) > eps)

%% Sanity check for program 11.
ii_ims1 = reshape(ii_ims, [361 100]);
fs1     = VecComputeFeature(ii_ims1, VecFeature(ftype, 19, 19));
fs2     = ComputeFeature(ii_ims, ftype);
assert(sum(fs1 - fs2') < eps, 'VecComputeFeature and ComputeFeature yield different results.');
fprintf('\nVecComputeFeature test passed.\n');