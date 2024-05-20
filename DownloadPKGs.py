import pkg_resources
import os

for package in ['pandas', 'statsmodels']:
    """Downloads pandas and statsmodels in /usr/bin/python3 if you do not have it"""
    try:
        dist = pkg_resources.get_distribution(package)
        print('{} ({}) is installed'.format(dist.key, dist.version))
    except pkg_resources.DistributionNotFound:
        print('{} is NOT installed'.format(package))
        os.system("/usr/bin/pip3 install " + package)
