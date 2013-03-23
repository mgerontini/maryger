function  ComputeROC(Cparams, Fdata, NFdata)
addpath('TrainingImages');

%Get Test Images
facenums = Fdata.fnums;
nonfacenums = NFdata.fnums;

%store test images
face_fnames = dir('TrainingImages/FACES/*.bmp');
total = 1:size(face_fnames);
%get Test data for faces
Fdiff = setdiff(total, facenums);

Nface_fnames = dir('TrainingImages/NFACES/*.bmp');
total = 1:size(face_fnames);
%get Test data for faces
NFdiff = setdiff(total, nonfacenums);

%preallocate score and labels
testsize = 5;
score = zeros(1, testsize*2);
labels = 2*ones(1, testsize*2);

%response for faces
for i = 1:size(Fdiff,2)
im_name = ['TrainingImages/FACES/',face_fnames(Fdiff(1,i)).name];
[img, ii_im] = LoadIm( im_name );
score(1,i) = ApplyDetector(Cparams,ii_im);
labels(1,i) = 1;
end

%response for NOnfaces
start = size(Fdiff,2) + 1
end_ = size(Fdiff,2) + size(NFdiff,2)
for i = start:end_  
im_name = ['TrainingImages/NFACES/',Nface_fnames(NFdiff(1,i-size(Fdiff,2))).name];
[img, ii_im] = LoadIm( im_name );
score(1,i) = ApplyDetector(Cparams,ii_im);
labels(1,i) = 0;
end


thres = 0;
%1. Choose a threshold to apply to the recorded scores
min_treshold = -100;
max_treshold = 100;
amount_treshold = 10000;
%step
step = (max_treshold - min_treshold)/amount_treshold ;


tpr_vector = zeros(1,amount_treshold);
fpr_vector = zeros(1,amount_treshold);
i = 0;

for t = min_treshold : step : max_treshold
i = i+1;

ntp = sum(labels & (score >= t));  
nfp = sum(~labels & (score >= t));

ntn =  sum(~labels & (score < t));
nfn = sum(labels & (score < t));

tpr_vector(1,i) = ntp / (ntp+nfn);
if( (ntp / (ntp+nfn))>0.7) 
    thres = t;end
fpr_vector(1,i) = nfp / (ntn+nfp);

end


Cparams =struct('alphas',Cparams.alphas,'Thetas',Cparams.Thetas,'fmat',Cparams.fmat,...
    'all_ftypes',Cparams.all_ftypes,'thres',thres);
save('Cparams')
%roc curve
plot( fpr_vector,tpr_vector)


end