%% Program 16 - Sanity Check
% Load saved data
Fdata = load('FaceData.mat');
NFdata = load('NonFaceData.mat');
FTdata = load('FeaturesToUse.mat');

% Compute feature responses for all images for every feature type
ni = size(Fdata.ii_ims,1);
nj = size(NFdata.ii_ims,1);
yfs = Fdata.ii_ims * FTdata.fmat(:,12028);
nfs = NFdata.ii_ims * FTdata.fmat(:,12028);
fs = [yfs;nfs];
n = size(fs,1);

% Initialize Weight vector
randVec = abs(randn(n,1));
ws = randVec/sum(randVec);

% Generate ys vector
ys = double([ones(size(yfs,1),1); zeros(size(nfs,1),1)]);

[theta, p, err] = LearnWeakClassifier(ws, fs, ys);

% Compute histogram of the feature responses from the face and non-face 
% images
[H,x1out] = hist(yfs);
[NH,x2out] = hist(nfs);

% Normalize frequencies of histograms
H = H/ni;
NH = NH/nj;

% Plot histograms
fig1 = figure;
hold on;
plot(x1out,H, 'r');
plot(x2out,NH);
line(theta, [0:0.005:max(NH)],'LineStyle','-');


%% Program 17
clear
fpic = MakeFeaturePic([4, 5, 5, 5, 5], 19, 19);
colormap(gray);
imagesc(fpic);
axis equal

%% Program 18
cpic = MakeClassifierPic(all_ftypes,[5192, 12765],[1.8725,1.467],...
    [1,-1],19,19);
colormap(gray);
imagesc(cpic);