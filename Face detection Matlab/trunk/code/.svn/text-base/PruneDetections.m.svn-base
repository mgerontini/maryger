function [ dets ] = PruneDetections( dets, N )
%PRUNEDETECTIONS Prune the detected regions.
%   Only list the top N regions; that is, the N regions that give the
%   highest score on the detector.

    dets = sortrows(dets, -5);
    dets = dets(1:N,:);

end

