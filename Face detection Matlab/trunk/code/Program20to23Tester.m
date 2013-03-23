%% Program 20 - Sanity Check
clear;
load('Cparams.mat');
Fdata       = load('FaceData.mat');
NFdata      = load('NonFaceData.mat');
[im, ii_im] = LoadIm('TrainingImages/FACES/face00001.bmp');
score       = ApplyDetector(Cparams, ii_im)

%% Program 21
ComputeROC(Cparams, Fdata, NFdata);
load('Cparams.mat');

%% Program 22
im_name     = 'TestImages/one_chris.png';
dets        = ScanImageFixedSize(Cparams, im_name);
DisplayDetections(im_name, dets)

%% Sanity check: do we get the same result with this, as with LoadIm and
% ApplyDetector?
test        = 'TrainingImages/FACES/face00001.bmp';
[im, ii_im] = LoadIm(test);
score       = ApplyDetector(Cparams, ii_im);
dets        = ScanImageFixedSize(Cparams, test);

%% Program 23
DisplayDetections(im_name, dets)

%% Program 24
im_name     = 'TestImages/big_one_chris.png';
dets        = ScanImageOverScale(Cparams, im_name, 0.6, 1.3, 0.06);
dets        = pruneDetections(dets, 25);
DisplayDetections(im_name, dets)








