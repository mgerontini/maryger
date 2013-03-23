%% Debug point 1.
[im, ii_im] = LoadIm('TrainingImages/FACES/face00001.bmp');
dinfo1      = load('DebugInfo/debuginfo1.mat');

% s1 and s2 should both be zero.
s1 = sum(abs(dinfo1.im(:) - im(:)) > eps)
s2 = sum(abs(dinfo1.ii_im(:) - ii_im(:)) > eps)