%%  Debug Point 7

% % Uncomment lines 3 to 9 if you want to run Debug Point 7 independently 
% % without running previous Debug Points
clear;
% % Load saved data
Fdata   = load('FaceData.mat');
NFdata  = load('NonFaceData.mat');
FTdata  = load('FeaturesToUse.mat');
dinfo7  = load('DebugInfo/debuginfo7.mat');
T       = dinfo7.T;
eps     = 0.1;
T = 100;
%profile on;
%matlabpool open 4;

tic
Cparams = FastBoostingAlg(Fdata, NFdata, FTdata, T);
toc
%profile off;
%profile viewer;

%matlabpool close;

sum(abs(dinfo7.alphas - Cparams.alphas)>eps)
sum(abs(dinfo7.Thetas(:) - Cparams.Thetas(:))>eps)

save('Cparams', 'Cparams');

%%  Display the classifier:
cpic = MakeClassifierPic(Cparams.all_ftypes,[1:10], Cparams.alphas,Cparams.Thetas(:,3),19,19);
colormap(gray);
imagesc(cpic)
