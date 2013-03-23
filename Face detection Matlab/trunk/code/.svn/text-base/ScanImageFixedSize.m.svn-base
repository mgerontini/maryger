function [dets] = ScanImageFixedSize3(Cparams, imName)

% Read image to be processed
im = imread(imName);

% Get dimensions
[rows cols d] = size(im);

% Convert im to grayscale if im is rgb
if(d==3)
    im =rgb2gray(im);
end

im = double(im);
fmat = Cparams.fmat;
all_ftypes = Cparams.all_ftypes;
dets = [];

% Compute integral of image
ii_im =  cumsum(cumsum(double(im))')';

% Compute integral of the image squared
im_square = im.*im; 
ii_im_square = cumsum(cumsum(double(im_square))')';

% Set window dimension
L = 19;

%write nested loops 
%normalize everything
%and apply detector
%get the faces
for x = 1:cols-L;
    for y = 1:rows-L;  
        % Calculate window and integral
        ii_window = ii_im(y:y+L-1, x:x+L-1);
        
        % Reshape window
        ii_window = ii_window(:);
       
        % Calculate mean and sigma for normalization
        A = ComputeBoxSum(ii_im, x, y, L, L);
        mu = (1/(L^2))*double(A);
        B = ComputeBoxSum(ii_im_square, x, y, L, L);
        sigma = sqrt((double(B) - (mu^2)*(L^2))*(1/((L^2)-1)));
        
        % Calculate non normalized features for window
        fs = ii_window' * fmat;
        fs = fs'/sigma;
        
        % Normalize features
        for i = 1:size(all_ftypes, 1)
            if all_ftypes(i,1)==3
                fs(i) = fs(i)+ ...
                    all_ftypes(i,4)*all_ftypes(i,5)*mu/sigma;
                %(L^2*mu)/sigma
            end
        end
        
        % Get H value 
        H = (Cparams.Thetas(:,3).*fs)<(Cparams.Thetas(:,3)...
            .*Cparams.Thetas(:,2)); 

        % Get score
        score = sum(Cparams.alphas.*H);
              
        if score >= 7%Cparams.thres
            dets = [dets; x, y , L,L];
        end

    end
end


end