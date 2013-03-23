%% Program 16 - Sanity Check
clear
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
wVal = 1/n;
wVec = repmat(wVal,n,1);
ws = wVec/sum(wVec); % this line can be ommitted

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
figure;
hold on;
plot(x1out,H, 'r');
plot(x2out,NH);
line(theta, [0:0.005:max(NH)],'LineStyle','-');


%% Program 17 - Sanity Check
clear
fpic = MakeFeaturePic([4, 5, 5, 5, 5], 19, 19);
colormap(gray);
imagesc(fpic);
axis equal

%% Program 18 - Sanity Check
clear
% Load saved data
FTdata = load('FeaturesToUse.mat');
cpic = MakeClassifierPic(FTdata.all_ftypes,[5192, 12765],[1.8725,1.467],...
    [1,-1],19,19);
colormap(gray)
imagesc(cpic)

%% Program 19 - Sanity Check
clear
% Load saved data
Fdata = load('FaceData.mat');
NFdata = load('NonFaceData.mat');
FTdata = load('FeaturesToUse.mat');

% Train Boosting Algorithm for whole 1000 features for T = 3
T = 3;
Cparams1 = TestBoostingAlg(Fdata, NFdata, FTdata, T);

fpic1 = MakeFeaturePic(Cparams1.all_ftypes(1,:),19,19);
fig1 = figure;
colormap(gray)
imagesc(fpic1)

fpic2 = MakeFeaturePic(Cparams1.all_ftypes(2,:),19,19);
fig2 = figure;
colormap(gray)
imagesc(fpic2)

fpic3 = MakeFeaturePic(Cparams1.all_ftypes(3,:),19,19);
fig3 = figure;
colormap(gray)
imagesc(fpic3)

cpic = MakeClassifierPic(Cparams1.all_ftypes,[1 2 3], Cparams1.alphas',...
    Cparams1.Thetas(:,3)',19,19);
fig4 = figure;
colormap(gray)
imagesc(cpic)

% Train BoostingAlg for all features for T = 1
T = 1;
Cparams2 = BoostingAlg(Fdata, NFdata, FTdata, T);

fpic5 = MakeFeaturePic(Cparams2.all_ftypes(1,:),19,19);
fig5 = figure;
colormap(gray)
imagesc(fpic5)

%% Print out figure 8 page 23

clear
load('Cparams.mat');

% Print all 10 features of Cparams
for i = 1:10
    fpic = MakeFeaturePic(Cparams.all_ftypes(i,:),19,19);
    fig = figure;
    colormap(gray)
    imagesc(fpic)
end

% Print the final outout combining all 10 features
cpic = MakeClassifierPic(Cparams.all_ftypes,[1:10], Cparams.alphas',...
    Cparams.Thetas(:,3)',19,19);
fig = figure;
colormap(gray)
imagesc(cpic)

