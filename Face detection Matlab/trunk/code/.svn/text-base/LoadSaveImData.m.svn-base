function ii_ims = LoadSaveImData( dirname, ni, im_sfn )
%LOADSAVEIMDATA Summary of this function goes here
%   Detailed explanation goes here
    face_fnames = dir(dirname);
    aa          = 3:length(face_fnames);
    a           = randperm(length(aa));
    fnums       = aa(a(1:ni));
    im          = imread([dirname, '/', face_fnames(fnums(1)).name]);
    ii_ims      = zeros(ni, size(im(:), 1));
    
    for i = 1:ni
        [im, ii_im] = LoadIm([dirname, '/', face_fnames(fnums(i)).name]);
        ii_ims(i,:) = ii_im(:);
    end
        
    save(im_sfn, 'dirname', 'fnums', 'ii_ims');
end

