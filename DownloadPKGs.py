import pkg_resources
import os

for package in ['pandas', 'statsmodels']:
    try:
        dist = pkg_resources.get_distribution(package)
        print('{} ({}) is installed'.format(dist.key, dist.version))
    except pkg_resources.DistributionNotFound:
        print('{} is NOT installed'.format(package))
        os.system("/usr/bin/pip3 install " + package)
