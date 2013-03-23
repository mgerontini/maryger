function [ fpic ] = MakeFeaturePic( ftype, W, H )
%MakeFeaturePic makes an image representing a feature, as in fig. 2 page 5 
% of the manual, given by the vector ftype
%
%   W: width of image
%   H: height of image
%   ftype:  array of feature parameters: [type, x, y, w, h]
%
%   fpic: zero image matrix (H x W) with -1s for black boxes and 1s for
%   white boxes 


% Extract feature parameters:
x       = ftype(2);
y       = ftype(3);
w       = ftype(4);
h       = ftype(5);

fpic = zeros(H,W);

switch ftype(1)
    case 1
        fpic(y:y+h-1, x:x+w-1) = -1; % black
        fpic(y+h:y+2*h-1, x:x+w-1) = 1; % white

    case 2
        fpic(y:y+h-1, x:x+w-1) = 1; % white
        fpic(y:y+h-1, x+w:x+2*w-1) = -1; % black
        
    case 3
        fpic(y:y+h-1, x:x+w-1) = 1; % white left
        fpic(y:y+h-1, x+w:x+2*w-1) = -1; % black
        fpic(y:y+h-1, x+2*w:x+3*w-1) = 1; % white right
       
    case 4
        fpic(y:y+h-1, x:x+w-1) = 1; % up white
        fpic(y:y+h-1, x+w:x+2*w-1) = -1; % up black
        fpic(y+h:y+2*h-1, x:x+w-1) = -1; % down black
        fpic(y+h:y+2*h-1, x+w:x+2*w-1) = 1; % down white
        
end


end

