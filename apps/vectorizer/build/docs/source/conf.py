# Configuration file for the Sphinx documentation builder.
#
# This file only contains a selection of the most common options. For a full
# list see the documentation:
# https://www.sphinx-doc.org/en/master/usage/configuration.html

# -- Path setup --------------------------------------------------------------

# If extensions (or modules to document with autodoc) are in another directory,
# add these directories to sys.path here. If the directory is relative to the
# documentation root, use os.path.abspath to make it absolute, like shown here.
#
# import os
# import sys
# sys.path.insert(0, os.path.abspath('.'))
import os
import sys
sys.path.insert(0, os.path.abspath('../../../'))

# -- Project information -----------------------------------------------------

project = 'vectorizer'
copyright = '___REPLACE-COPYRIGHT-YEAR___, ___REPLACE-COPYRIGHT-NAME___'
author = 'sUeharaE4'

# The full version, including alpha/beta/rc tags
release = '0.0.1'


# -- General configuration ---------------------------------------------------

# Add any Sphinx extension module names here, as strings. They can be
# extensions coming with Sphinx (named 'sphinx.ext.*') or your custom
# ones.
extensions = [
    'sphinx.ext.autodoc', 'sphinx.ext.napoleon', 'sphinx.ext.viewcode',
    'sphinx_autodoc_typehints'
]

# Add any paths that contain templates here, relative to this directory.
templates_path = ['_templates']

# List of patterns, relative to source directory, that match files and
# directories to ignore when looking for source files.
# This pattern also affects html_static_path and html_extra_path.
exclude_patterns = []


# -- Options for HTML output -------------------------------------------------

# The theme to use for HTML and HTML Help pages.  See the documentation for
# a list of builtin themes.
#
# html_theme = 'alabaster'
html_theme = 'sphinx_rtd_theme'

# Add any paths that contain custom static files (such as style sheets) here,
# relative to this directory. They are copied after the builtin static files,
# so a file named "default.css" will overwrite the builtin "default.css".
html_static_path = ['_static']

autodoc_default_options = {
    'member-order': 'bysource',
    'private-members': True,
    'special-members': '__init__',
    'undoc-members': True,
    'inherited-members': False,
    'show-inheritance': True,
    'autodoc_typehints': 'description',
}

# -- Options for skipping specific docs --
skip_blacklist = frozenset(
    [
        '__weakref__', '__module__', '__doc__', '__abstractmethods__',
        '__hyperparam_spec__', '__hyperparam_trans_dict__',
        '__param_init_spec__'
    ]
)
skip_whitelist = frozenset(['', ''])


def handle_skip(app, what, name, obj, skip, options):
    if name.startswith('_abc_') or name in skip_blacklist:
        return True
    if name.startswith('__testcase'):
        return False
    if (name.startswith('__') and name.endswith('__') and
            getattr(obj, '__doc__', None)):
        return False
    return skip


def setup(app):
    app.connect("autodoc-skip-member", handle_skip)